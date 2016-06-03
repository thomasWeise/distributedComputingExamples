package wordCount;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * This is the mapper part of the word count example. The mapper receives
 * lines of text. It first replaces all punctuation marks with spaces. Then
 * it breaks the line at the spaces into multiple words. For each word, we
 * emit a tuple of form {@code <WORD, 1>}.
 */
public class WordCountMapper
    extends Mapper<LongWritable, Text, Text, IntWritable> {

  public static final IntWritable ONE = new IntWritable(1);

  @Override
  protected void map(final LongWritable offset, final Text line,
      final Context context) throws IOException, InterruptedException {
    for (String word : line.toString()// replace punctuation and other
        .replace('.', ' ').replace(',', ' ').replace('/', ' ')// strange
        .replace(']', ' ').replace('[', ' ').replace('_', ' ')// chars
        .replace(')', ' ').replace('(', ' ').replace('#', ' ')// with
        .replace('!', ' ').replace('?', ' ').replace("-", "")// spaces
        .replace("\"", "").replace("\'", "").replaceAll("[0-9]+", " ")//
        .replace(':', ' ').replace('\t', ' ').replace('\f', ' ')//
        .split("\\s+")) {// iterate over all space-separated words
      word = word.trim();
      if (word.length() > 0) {// emit one tuple <WORD, 1> for each WORD
        context.write(new Text(word.toLowerCase()), WordCountMapper.ONE);
      }
    }
  }
}
