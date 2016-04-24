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

public class WebFinderReducer
    extends Reducer<Text, Text, Text, List<Text>> {

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
      string = url.toString();
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
      set.add(add);
    }

    if ((size = set.size()) > 1) {
      list = new ArrayList(size);
      for (final URL found : set) {
        list.add(found.toString());
      }
      Collections.sort(list);
      for (index = list.size(); (--index) >= 0;) {
        list.set(index, new Text((String) (list.get(index))));
      }
      context.write(key, list);
    }
  }
}
