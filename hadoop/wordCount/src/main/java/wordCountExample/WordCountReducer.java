package wordCountExample;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class WordCountReducer
    extends Reducer<Text, IntWritable, Text, IntWritable> {

  @Override
  protected void reduce(final Text key, final Iterable<IntWritable> values,
      final Context context) throws IOException, InterruptedException {

    int count = 0;
    for (final IntWritable current : values) {
      count += current.get();
    }
    context.write(key, new IntWritable(count));
  }

}
