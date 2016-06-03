package wordCount;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * This is the reducer class of the MapReduce Word Count example. It also
 * acts as combiner, i.e., it is applied two times:
 * <ol>
 * <li>As combiner, i.e., directly after the mapping process on each mapper
 * node. This way, the data is "reduced" before being sent on. During this
 * application, all input tuples of the reducer are of the form
 * {@code <WORD, List<1, 1, 1, ... 1>>}. The reducer here creates output
 * tuples of the form {@code <WORD, Sum(List...)>}. These tuples have the
 * same format as the mapper's output tuples, just with integers which
 * might be larger than 1. They are sent on to the real reducer steps.</li>
 * <li>As actual reducer: Here all the tuples produced by the combiners
 * arrive. There may be multiple entries in the lists of these tuples,
 * since the same word may have been found on different computers.</li>
 * </ol>
 */
public class WordCountReducer
    extends Reducer<Text, IntWritable, Text, IntWritable> {

  @Override
  protected void reduce(final Text key, final Iterable<IntWritable> values,
      final Context context) throws IOException, InterruptedException {
    // we receive tuples of the type <WORD, IntWritable> for each WORD
    int count = 0;
    for (final IntWritable current : values) { // we add up all the ints
      count += current.get();
    }
    context.write(key, new IntWritable(count));// and emit the final count
  }
}
