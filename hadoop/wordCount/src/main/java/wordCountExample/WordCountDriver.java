package wordCountExample;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class WordCountDriver extends Configured implements Tool {

  public static void main(final String[] args) throws Exception {
    try {
      final int res = ToolRunner.run(new Configuration(),
          new WordCountDriver(), args);
      System.exit(res);
    } catch (final Exception e) {
      e.printStackTrace();
      System.exit(255);
    }
  }

  @Override
  public int run(final String[] args) throws Exception {

    final Configuration conf = new Configuration();
    final Job job = Job.getInstance(conf, "Your job name");

    job.setJarByClass(WordCountDriver.class);

    if (args.length < 2) {
      return 1;
    }

    job.setMapperClass(WordCountMapper.class);

    // job.setMapOutputKeyClass(Text.class);
    // job.setMapOutputValueClass(IntWritable.class);

    job.setReducerClass(WordCountReducer.class);
    job.setCombinerClass(WordCountReducer.class);

    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(IntWritable.class);

    job.setInputFormatClass(TextInputFormat.class);

    job.setOutputFormatClass(TextOutputFormat.class);

    final Path filePath = new Path(args[0]);
    FileInputFormat.setInputPaths(job, filePath);

    final Path outputPath = new Path(args[1]);
    FileOutputFormat.setOutputPath(job, outputPath);

    job.waitForCompletion(true);
    return 0;
  }
}
