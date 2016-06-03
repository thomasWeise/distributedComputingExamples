package wordCount;

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
    System.exit(ToolRunner.run(new Configuration(), //
        new WordCountDriver(), args));
  }

  @Override
  public int run(final String[] args) throws Exception {
    final Configuration conf;
    final Job job;

    conf = new Configuration();
    job = Job.getInstance(conf, "Word Count Map-Reduce");

    job.setJarByClass(WordCountDriver.class);

    if (args.length < 2) {
      return 1;
    }

    job.setMapperClass(WordCountMapper.class);// set mapper
    job.setReducerClass(WordCountReducer.class);// set reducer
    // a combiner performs something like a reduction step right after
    // mapping, on the mapper's computer, before sending on the data
    job.setCombinerClass(WordCountReducer.class);// set combiner

    job.setOutputKeyClass(Text.class);// set output key class
    job.setOutputValueClass(IntWritable.class);// set output value class

    job.setInputFormatClass(TextInputFormat.class);// set input format
    job.setOutputFormatClass(TextOutputFormat.class);// set output format

    FileInputFormat.setInputPaths(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));

    job.waitForCompletion(true);
    return 0;
  }
}
