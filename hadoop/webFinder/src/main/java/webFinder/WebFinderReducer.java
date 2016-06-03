package webFinder;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * This is the reducer component of the web finder example. For each key (
 * {@code resource URL}) of the tuples produced by the mapper, it receives
 * the list of all values ({@code website URLs}). If such a list contains
 * more than one unique element, this means that the resource is shared by
 * multiple websites. This reducer emits tuples of the form
 * {@code <resource URL, list of website urls>}.
 */
public class WebFinderReducer
    extends Reducer<Text, Text, Text, List<Text>> {

  /**
   * The actual reduction step: From the tuples of form
   * {@code <resource URL, iterable of referencing website URLs>}, select
   * all resources referenced by more than one unique website. For these,
   * output tuples of the form {@code <resource URL, list of website URLs>}
   * .
   */
  @Override
  protected void reduce(final Text key, final Iterable<Text> values,
      final Context context) throws IOException, InterruptedException {
    final HashSet<URL> set;
    final int size;
    final ArrayList list;
    String string;
    URL add;
    int index;

    set = new HashSet<>();
    looper: for (final Text url : values) {
      string = url.toString();// convert value to a URL
      try {
        add = new URI(string).normalize().toURL();
      } catch (@SuppressWarnings("unused") final Throwable error) {
        try {
          add = new URL(string).toURI().normalize().toURL();
        } catch (@SuppressWarnings("unused") final Throwable error2) {
          try {
            add = new URL(string);
          } catch (@SuppressWarnings("unused") final Throwable error3) {
            continue looper;
          }
        }
      }
      set.add(add); // store value in set of URLs pointing to this resource
    }

    if ((size = set.size()) > 1) {// multiple URLs point to key
      list = new ArrayList(size);// let's make a list of them
      for (final URL found : set) {
        list.add(found.toString());
      }
      Collections.sort(list);// and sort them
      for (index = list.size(); (--index) >= 0;) {// now convert to Text
        list.set(index, new Text((String) (list.get(index))));
      }
      context.write(key, list);// write <key, list of referers> tuple
    }
  }
}
