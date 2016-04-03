package proxy;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * This is the proxy servlet. Being a proxy, it accepts requests from a web
 * browser that are for another web server. It forwards these requests to
 * the other, real web server. It then forwards the response of the other
 * web server back to the web browser. While doing so, it also
 * automatically forwards cookies and other header fields between the web
 * browser and web server and vice versa.
 * </p>
 * <p>
 * It has two methods where "enhancements" may be added:
 * </p>
 * <ol>
 * <li>Method {@link #makeURL(HttpServletRequest)} creates the "target URL"
 * from the incoming request, i.e., the URL to the actual resource on the
 * actual web server. In this method, the target URL is created genuinely,
 * but it may be enhanced using additional code. Or a request may be
 * redirected to another URL.</li>
 * <li>Method {@link #forwardText(URLConnection, OutputStream)} is called
 * for all HTML content transfered between the web server and the client.
 * It first loads the complete text content and then forwards it to the
 * client. After the text content has been loaded from the web server and
 * before it is forwarded to the web browser, it may be enhanced.</li>
 * </ol>
 */
public class ProxyServlet extends HttpServlet {

  /** the serial version UID */
  private static final long serialVersionUID = 1L;

  /** create the servlet */
  public ProxyServlet() {
    super();
  }

  /**
   * <p>
   * This method handles a HTTP Get request. It does the following:
   * </p>
   * <ol>
   * <li>From the incoming request, it determines the URL the web browser
   * is actually looking for, i.e., the real resource on the real web
   * server. A connection to this server is established. This is all done
   * in method
   * {@link #initConnection(HttpServletRequest, HttpServletResponse)}. If
   * this method returns a non-{@code null} value, everything is OK and we
   * may proceed.</li>
   * <li>It tries to determine whether the real web server is sending us
   * HTML or something else (maybe an image or sound or CSS or JavaScript
   * or whatever). This is done by checking the
   * {@linkplain URLConnection#getContentType() content type} of the
   * response that we got from the connection the actual web server.</li>
   * <li>If the server is sending us HTML or XHTML, we first load this text
   * completely and then forward it to the web browser. While doing this,
   * we try to respect the text encoding used by the web server. This all
   * happens in method {@link #forwardText(URLConnection, OutputStream)}.
   * </li>
   * <li>Otherwise, we just shovel all incoming bytes to the web browser
   * directly via method
   * {@link #forwardRawBytes(InputStream, OutputStream, byte[])}.</li>
   * </ol>
   *
   * @param req
   *          the request
   * @param resp
   *          the response
   */
  @Override
  public void doGet(final HttpServletRequest req,
      final HttpServletResponse resp) {
    URLConnection connex;
    String contentType;

    try {
      connex = ProxyServlet.initConnection(req, resp);
      if (connex == null) {
        return; // nothing to do, there was an error
      }

      doContent: {
        // Notice: If this was just about efficient proxying, we would
        // always do forwardRawBytes. Using the method forwardText makes
        // only sense if we want to "enhance" the text context.
        contentType = connex.getContentType();
        if (contentType != null) {
          // is there a content type specified?
          contentType = contentType.toLowerCase();
          // let's check for HTML or XHTML
          if ((contentType.contains("html") || // //$NON-NLS-1$
              contentType.contains("xhtml"))) { //$NON-NLS-1$
            // yes, it is HTML/XHTML: process as text
            ProxyServlet.forwardText(connex, resp.getOutputStream());
            break doContent; // done
          }
        } // either no content type or not HTML/XHTML: send binary
        ProxyServlet.forwardRawBytes(connex.getInputStream(),
            resp.getOutputStream(), new byte[8192]);
      }
    } catch (final Throwable error) {
      // prevent about errors to show up in browser
      try {
        resp.sendError(404);// let's claim the page doesn't exist
        error.printStackTrace();
      } catch (final Throwable error2) {
        error2.printStackTrace();
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public void doPost(final HttpServletRequest req,
      final HttpServletResponse resp)
          throws ServletException, IOException {
    this.doGet(req, resp); // now this is dodgy, but let's give it a shot
  }

  /**
   * <p>
   * Build the URL that the web browser was using. This means to first get
   * the {@link HttpServletRequest#getRequestURL()}. If there is a
   * {@link HttpServletRequest#getQueryString() query string}, we attach
   * it.
   * </p>
   * <p>
   * During this process, the URL may be "enhanced".
   * </p>
   *
   * @param request
   *          the servlet request
   * @return the URL
   * @throws IOException
   *           if the URL was malformed
   */
  public static final URL makeURL(final HttpServletRequest request)
      throws IOException {
    final StringBuffer url;

    url = request.getRequestURL();
    if (request.getQueryString() != null) {
      url.append('?');
      url.append(request.getQueryString());
    }

    try {
      return new URL(url.toString());
    } catch (final Exception error) {
      throw new IOException("Could not construct url for request.", //$NON-NLS-1$
          error);
    }
  }

  /**
   * <p>
   * Forward text data to the output writer of the servlet response. This
   * method does the following things:
   * </p>
   * <ol>
   * <li>First, it tries to detect which character encoding is used to
   * transfer the text. If a specific encoding is used, we will use it as
   * well.</li>
   * <li>An {@linkplain InputStreamReader input stream reader} is opened,
   * wrapping the {@linkplain InputStream input stream} connected to the
   * web server. All text is read from the web server into a
   * {@linkplain StringBuilder string builder}. If an encoding was
   * detected, this encoding is used by the reader.</li>
   * <li>An {@linkplain OutputStreamWriter output stream writer} is opened,
   * wrapping the {@linkplain OutputStream output stream} connected to the
   * web browser. The text in the {@linkplain StringBuilder string builder}
   * is written to that writer. If an encoding was detected, this encoding
   * is used by the reader.</li>
   * <li>All streams, readers, and writers are closed.</li>
   * </ol>
   * <p>
   * This sequence of first reading all the text and then forwarding it
   * allows for an intermediate step of "enhancing" the text.
   * </p>
   *
   * @param connection
   *          the connection to the real server
   * @param out
   *          the output stream
   * @throws IOException
   *           if i/o fails
   */
  @SuppressWarnings("resource")
  public static final void forwardText(final URLConnection connection,
      final OutputStream out) throws IOException {
    final StringBuilder builder;
    String encoding;
    InputStreamReader inputStreamReader;
    OutputStreamWriter outputStreamWriter;
    int start, end, size;
    char[] buffer;

    // first, let us try to guess the encoding
    findEncoding: {
      encoding = connection.getContentType();
      if (encoding != null) {
        // encodings in HTTP are part of the content type
        // looking like Content-Type: text/html; charset=utf-8
        encoding = encoding.toLowerCase();
        // we first look for "charset"
        start = encoding.indexOf("charset"); //$NON-NLS-1$
        if (start > 0) {
          start += 7;
          // then for the following "="
          start = encoding.indexOf('=', start);
          if (start > 0) {
            ++start;
            // then there comes the encoding name and
            // afterwards either an ";" or the end of the line
            end = encoding.indexOf(';', start);
            if (end < start) {
              end = encoding.length();
            }
            if (end > start) {
              encoding = encoding.substring(start, end);
              break findEncoding; // got the encoding
            }
          }
        }
      }
      // if we get here, no encoding was specified
      encoding = null;
    }

    // time to read the text!
    try (final InputStream inputStream = connection.getInputStream()) {
      // Having the encoding, we can now try to open a reader using it
      openReader: {
        if (encoding != null) {
          try {
            inputStreamReader = new InputStreamReader(inputStream,
                encoding);
            break openReader; // encoding can be used, reader is open
          } catch (final Throwable error) {
            error.printStackTrace(); // encoding cannot be used
          }
        }
        encoding = null;// don't have or don't understand the encoding
        // open plain reader and hope for the best
        inputStreamReader = new InputStreamReader(inputStream);
      }

      try {
        // time to load the content into a string builder
        buffer = new char[8192];
        builder = new StringBuilder(
            Math.max(8192, 2 * connection.getContentLength()));

        while ((size = inputStreamReader.read(buffer)) >= 0) {
          builder.append(buffer, 0, size);
        }
      } finally {// and close the reader
        inputStreamReader.close();
      }
    } // and close the input stream, as it is no longer needed

    // now "builder" is filled with the complete text of the web page
    // we may "enhance" this text here

    // all text has been loaded and potentially "enhanced"
    // time to send it to the browser using the same encoding
    try (final OutputStream outputStream = out) {
      openWriter: {
        if (encoding != null) {
          try {// open the writer with the right encoding
            outputStreamWriter = new OutputStreamWriter(outputStream,
                encoding);
            break openWriter;// encoding can be used, writer open
          } catch (final Throwable error) {
            error.printStackTrace();// failure??? odd!!
          }
        }
        encoding = null;// don't have or don't understand the encoding
        // open plain writer and hope for the best
        outputStreamWriter = new OutputStreamWriter(outputStream);
      }

      try { // well, just write the contents
        outputStreamWriter.write(builder.toString());
      } finally {// and close the writer
        outputStreamWriter.close();
      }
    }
  }

  /**
   * This method does the following things:
   * <ol>
   * <li>We use {@link #makeURL(HttpServletRequest)} to obtain the URL that
   * the web browser was pointed to.</li>
   * <li>We set up a connection to a the "real" server. For this purpose,
   * all the HTTP header fields that the web browser has sent to use (the
   * proxy) are also set to the same value in the new connection, i.e.,
   * will be forwarded from the incoming {@code request} to the real web
   * server. This is needed to pass cookies from the client to the server,
   * for instance, i.e., to have sessions. We disable "keep-alive" for
   * connections, as we cannot do that. We also disable "Accept-Encoding",
   * as this would allow the server to answer us with gzipped HTML, which
   * we then cannot "beautify" anymore.</li>
   * <li>We set this connection as read-only, set some time outs, and open
   * it.</li>
   * <li>Normally, the connection will be a {@link HttpURLConnection}. If
   * it is, we can access the status code and check whether it was
   * successfully established (codes like {@code 2XX}) or there was an
   * error (any other code). In the latter case, we will return
   * {@code null} at the end of this method.</li>
   * <li>We load all the header fields that the real server has sent to use
   * via the connection and also set them in our {@code response} to the
   * web browser. This is needed to allow cookies to be sent from the
   * server to the client (browser).</li>
   * <li>We then return the connection object. We can now use
   * {@link URLConnection#getContentType()} to determine whether the
   * contents of the connection's {@link URLConnection#getInputStream()}
   * should be passed directly to the browser (as is the case for images
   * and stuff) or "beautified", as is the case for HTML.</li>
   * </ol>
   *
   * @param request
   *          the request
   * @param response
   *          the response to which we should store the connection's meta
   *          information
   * @return the connection, or {@code null} if there was an error
   * @throws IOException
   *           if the connection fails
   */
  public static final URLConnection initConnection(
      final HttpServletRequest request, final HttpServletResponse response)
          throws IOException {
    final URLConnection connection;
    final URL url;
    final Enumeration<String> headerNames;
    final HttpURLConnection httpConnection;
    int field, status;
    String name, value;

    url = ProxyServlet.makeURL(request);

    connection = url.openConnection();
    // set basic connection properties
    connection.setDoInput(true);
    connection.setDoOutput(false);
    connection.setConnectTimeout(90000);
    connection.setReadTimeout(90000);

    // forward incoming header fields from the browser to the real server
    headerNames = request.getHeaderNames();
    if (headerNames != null) {
      while (headerNames.hasMoreElements()) {
        name = headerNames.nextElement();
        if ("Connection".equalsIgnoreCase(name)) { //$NON-NLS-1$
          continue; // don't allow keep-alive
        }
        if ("Accept-Encoding".equalsIgnoreCase(name)) { //$NON-NLS-1$
          continue; // don't allow gzip or deflate
        }
        connection.setRequestProperty(name, request.getHeader(name));
      }
    }

    connection.connect();

    // The connection should be an HTTP connection
    if (connection instanceof HttpURLConnection) {
      // And yes it is. So let's check its status code
      httpConnection = ((HttpURLConnection) connection);
      status = httpConnection.getResponseCode();
      if ((status / 100) == 2) {// all 2XX codes indicate OK
        response.setStatus(status);
      } else {// anything else means error
        response.sendError(status, httpConnection.getResponseMessage());
      }
    } else {
      status = 200; // not HTTP, maybe FTP or whatever, just assume OK
    }

    response.setContentType(connection.getContentType());

    // forward header fields from the web server to the browser
    for (field = 0; //
    (name = connection.getHeaderFieldKey(field)) != null; //
    ++field) {
      if ("Content-Length".equalsIgnoreCase(name)) { //$NON-NLS-1$
        continue; // let servlet container handler it
      }
      if ("Content-MD5".equalsIgnoreCase(name)) { //$NON-NLS-1$
        continue; // let servlet container handler it
      }
      value = connection.getHeaderField(field);
      if ("Transfer-Encoding".equalsIgnoreCase(name)) { //$NON-NLS-1$
        if ("chunked".equalsIgnoreCase(value)) {//$NON-NLS-1$
          continue; // let servlet container handler it
        }
      }
      response.setHeader(name, connection.getHeaderField(field));
    }

    if ((status / 100) != 2) {
      return null; // nothing to do, there was an error
    }
    return connection;
  }

  /**
   * Copy all data from an input stream to an output stream.
   *
   * @param inputStream
   *          the input stream
   * @param outputStream
   *          the output stream
   * @param buffer
   *          the buffer to be used for intermediate reads
   * @throws IOException
   *           if i/o fails
   */
  public static final void forwardRawBytes(final InputStream inputStream,
      final OutputStream outputStream, final byte[] buffer)
          throws IOException {
    int read;
    try {
      while ((read = inputStream.read(buffer)) > 0) {
        outputStream.write(buffer, 0, read);
      }
    } finally {
      try {
        inputStream.close();
      } finally {
        outputStream.close();
      }
    }
  }
}
