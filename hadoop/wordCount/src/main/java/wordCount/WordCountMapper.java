package wordCount;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class WordCountMapper
    extends Mapper<LongWritable, Text, Text, IntWritable> {

  public static final IntWritable ONE = new IntWritable(1);

  @Override
  protected void map(final LongWritable offset, final Text line,
      final Context context) throws IOException, InterruptedException {
    for (String word : line.toString()//
        .replace('.', ' ').replace(',', ' ').replace('/', ' ')//
        .replace(']', ' ').replace('[', ' ').replace('_', ' ')//
        .replace(')', ' ').replace('(', ' ').replace('#', ' ')//
        .replace('!', ' ').replace('?', ' ').replace("-", "")//
        .replace("\"", "").replace("\'", "").replaceAll("[0-9]+", " ")//
        .replace(':', ' ').replace('\t', ' ').replace('\f', ' ')//
        .split("\\s+")) {
      word = word.trim();
      if (word.length() > 0) {
        context.write(new Text(word.toLowerCase()), WordCountMapper.ONE);
      }
    }
  }
}
