package webFinder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.log4j.Logger;

public class WebFinderMapper
    extends Mapper<LongWritable, Text, Text, Text> {

  /** the logger we use */
  private static Logger LOGGER = Logger.getLogger(WebFinderMapper.class);

  @Override
  protected void map(final LongWritable offset, final Text line,
      final Context context) throws IOException, InterruptedException {
    final URL baseUrl;
    final URI baseUri;
    final int maxDepth;
    final Text baseUrlText;
    final HashSet<URL> done;
    String str;

    maxDepth = context.getConfiguration().getInt("maxDepth", 1);

    str = WebFinderMapper.__prepare(line.toString(), true);
    if (str == null) {
      return;
    }

    baseUri = URI.create(str).normalize();
    baseUrl = baseUri.toURL();
    done = new HashSet<>();
    done.add(baseUrl);
    try {
      done.add(new URL(baseUrl.toString() + '/'));
    } catch (@SuppressWarnings("unused") final Throwable error) {
      // ignore
    }
    baseUrlText = new Text(baseUrl.toString());
    context.write(baseUrlText, baseUrlText);
    WebFinderMapper.__load(maxDepth, baseUrl, baseUrlText, baseUrl,
        baseUri, new StringBuilder(), new char[16384], done, context);
  }

  /**
   * load a given URL
   *
   * @param remainingDepth
   *          how deep we can still go
   * @param baseUrl
   *          the base url
   * @param baseUrlText
   *          the base url text
   * @param loadUrl
   *          the url to load
   * @param loadUri
   *          the uri equivalent to loadUrl
   * @param stringBuilder
   *          the string builder used for buffering
   * @param buffer
   *          the character buffer
   * @param done
   *          the set of already-processed urls
   * @param out
   *          the output context
   */
  private static final void __load(final int remainingDepth,
      final URL baseUrl, final Text baseUrlText, final URL loadUrl,
      final URI loadUri, final StringBuilder stringBuilder,
      final char[] buffer, final HashSet<URL> done, final Context out) {
    final String text, lower;
    String test;
    int index1, index2, last;
    URI nextUri;
    URL nextUrl;
    URLConnection uconn;
    char sep;

    if ((WebFinderMapper.LOGGER != null)
        && WebFinderMapper.LOGGER.isInfoEnabled()) {
      WebFinderMapper.LOGGER.info("Now tracing URL '" + loadUrl
          + "' for base url '" + baseUrl + "'.");
    }

    try {
      int read;

      stringBuilder.setLength(0);
      uconn = loadUrl.openConnection();
      uconn.setConnectTimeout(10_000);
      uconn.setReadTimeout(10_000);
      uconn.setDoInput(true);
      uconn.setDoOutput(false);
      uconn.setUseCaches(true);
      uconn.setDefaultUseCaches(true);
      try (final InputStream inputStream = loadUrl.openStream()) {
        try (final InputStreamReader inputReader = new InputStreamReader(
            inputStream)) {
          while ((read = inputReader.read(buffer)) > 0) {
            stringBuilder.append(buffer, 0, read);
          }
        }
      }

      text = stringBuilder.toString().replace('\n', ' ')//
          .replace('\r', ' ').replace('\t', ' ').replaceAll("  ", " ");
      lower = text.toLowerCase();

      nextDesc: for (final __LinkDesc desc : WebFinderMapper.DESCS) {

        last = 0;// find and load scripts
        findDesc: for (;;) {
          index1 = lower.indexOf(desc.m_begin, last);
          if (index1 <= last) {
            continue nextDesc;
          }
          last = index1 + desc.m_begin.length();
          index1 = lower.indexOf(desc.m_urlIndicatorQuote, last);
          index2 = lower.indexOf(desc.m_urlIndicatorPrime, last);
          sep = '"';
          if (index2 > last) {
            if ((index2 < index1) || (index1 < last)) {
              index1 = index2;
              sep = '\'';
            }
          }
          index2 = lower.indexOf('>', last);
          if (index1 <= last) {
            continue nextDesc;
          }
          if ((index2 < index1) && (index2 >= last)) {
            last = index2;// careful: there is a url inside a script
            continue findDesc;// i.e., no script resource: quit
          }
          last = index1 + desc.m_urlIndicatorQuote.length();
          index1 = lower.indexOf(sep, last);
          if (index1 < last) {
            continue nextDesc;
          }

          test = text.substring(last, index1);
          last = index1;
          test = WebFinderMapper.__prepare(test, desc.m_loadRecursive);
          if (test == null) {
            continue findDesc;
          }

          // let's see whether we can transform the link to a URL and URI
          try {
            nextUri = loadUri.resolve(test).normalize();
            nextUrl = nextUri.toURL();
          } catch (final Throwable error) {
            try {
              nextUrl = new URL(loadUrl.toString() + '/' + test);
              nextUri = nextUrl.toURI().normalize();
            } catch (final Throwable error2) {
              try {
                nextUrl = new URL(test);
                nextUri = nextUrl.toURI().normalize();
                nextUrl = nextUri.toURL();
              } catch (final Throwable error3) {
                error.addSuppressed(error2);
                error.addSuppressed(error3);
                if (WebFinderMapper.LOGGER != null) {
                  WebFinderMapper.LOGGER
                      .warn("Error while trying to build URL with string '"
                          + test + "' under load URL '"
                          + loadUrl.toString() + "' for base URL '"
                          + baseUrl.toString() + "'.", error2);
                }
                continue findDesc;
              }
            }
          }

          try {// http://x.y.z/ == http://x.y.z
            test = nextUri.toString();
            index1 = test.length();
            if (index1 > 0) {
              if (test.charAt(--index1) == '/') {
                test = test.substring(0, index1);
                try {
                  nextUri = new URI(test).normalize();
                  nextUrl = nextUri.toURL();
                } catch (@SuppressWarnings("unused") final Throwable ignore2) {
                  nextUrl = new URL(test);
                  nextUri = nextUrl.toURI();
                }
              }
            }
          } catch (@SuppressWarnings("unused") final Throwable ignore) {
            // we can ignore this
          }

          if (done.add(nextUrl)) {
            if (out != null) {
              out.write(new Text(nextUrl.toString()), baseUrlText);
            }
            if (desc.m_loadRecursive && (remainingDepth > 0)) {
              WebFinderMapper.__load((remainingDepth - 1), baseUrl,
                  baseUrlText, nextUrl, nextUri, stringBuilder, buffer,
                  done, out);
            }
          }
        }
      }
    } catch (final Throwable error) {
      if (WebFinderMapper.LOGGER != null) {
        WebFinderMapper.LOGGER.warn(
            "Error while trying to load URL '" + loadUrl + "'.", error);
      }
    }

    if ((WebFinderMapper.LOGGER != null)
        && WebFinderMapper.LOGGER.isInfoEnabled()) {
      WebFinderMapper.LOGGER.info("Finished tracing URL '" + loadUrl
          + "' for base url '" + baseUrl + "'.");
    }
  }

  /**
   * prepare a uri fragment
   *
   * @param raw
   *          the raw string
   * @param forTrace
   *          for tracing or just checking
   * @return the prepared string
   */
  private static final String __prepare(final String raw,
      final boolean forTrace) {
    String test, testLC;
    int last, other, length;

    test = raw.trim();
    length = test.length();
    if (length <= 0) {
      return null;
    }

    testLC = test.toLowerCase();
    if (testLC.startsWith("javascript:") || //
        testLC.startsWith("avascript:")) {
      return null;
    }

    if (testLC.startsWith("ttp://")) {
      test = 'h' + test;// fix some strange errors
      ++length;
    } else {
      if (testLC.startsWith("tp://")) {
        test = "ht" + test;// fix some strange errors
        length += 2;
      }
    }

    last = test.indexOf('#');
    if (last < 0) {
      last = length;
    }

    if (!forTrace) {
      other = test.indexOf('?');
      if ((other >= 0) && (other < last)) {
        last = other;
      }
    }

    if (last < length) {
      if (last > 0) {
        switch (test.charAt(last - 1)) {
          case '/':
          case '#': {
            --last;
          }
        }
      }
      if (last <= 0) {
        return null;
      }
      return test.substring(0, last).trim();
    }

    return test;
  }

  /** test */
  public static final void main(final String[] args) throws Throwable {
    final URI uri;
    final URL url;
    final HashSet<URL> done;

    uri = new URI("http://www.qq.com");
    url = uri.toURL();
    done = new HashSet<>();
    done.add(url);
    try {
      done.add(new URL(url.toString() + '/'));
    } catch (@SuppressWarnings("unused") final Throwable error) {
      // ignore
    }
    WebFinderMapper.__load(2, url, new Text(url.toString()), url, uri,
        new StringBuilder(), new char[16384], done, null);
  }

  /** the link descriptions */
  static final __LinkDesc[] DESCS = { //
      new __LinkDesc(false, "<link rel=\"stylesheet\"", "href="), //
      new __LinkDesc(false, "<link rel='stylesheet'", "href="), //
      new __LinkDesc(false, "<img", "src="), //
      new __LinkDesc(false, "<script", "src="), //
      new __LinkDesc(true, "<iframe", "src="), //
      new __LinkDesc(true, "<frame", "src="), //
      new __LinkDesc(true, "<a", "href="), //
  };

  /** the description of a way to find a link or resource */
  private static final class __LinkDesc {
    /** is this a resource that we need to load recursively? */
    final boolean m_loadRecursive;
    /** the beginning string */
    final String m_begin;
    /** the url indicator with quote */
    final String m_urlIndicatorQuote;
    /** the url indicator with prime */
    final String m_urlIndicatorPrime;

    /**
     * the description of a link
     *
     * @param loadRecursive
     *          is this a resource that we need to load recursively?
     * @param begin
     *          the beginning string
     * @param urlIndicator
     *          the url indicator
     */
    __LinkDesc(final boolean loadRecursive, final String begin,
        final String urlIndicator) {
      super();
      this.m_loadRecursive = loadRecursive;
      this.m_begin = begin;
      this.m_urlIndicatorQuote = urlIndicator + '"';
      this.m_urlIndicatorPrime = urlIndicator + '\'';
    }
  }
}