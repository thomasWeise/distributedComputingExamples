package webFinder;

import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 * The driver of the web finder sets up the distributed computation by
 * defining what the mapper and reducer classes, amongst other things.
 */
public class WebFinderDriver extends Configured implements Tool {

  public static void main(final String[] args) throws Exception {
    try {
      final int res = ToolRunner.run(new Configuration(),
          new WebFinderDriver(), args);
      System.exit(res);
    } catch (final Exception e) {
      e.printStackTrace();
      System.exit(255);
    }
  }

  /** Setting up the computation. */
  @Override
  public int run(final String[] args) throws Exception {
    final Configuration conf;
    final Job job;

    conf = new Configuration();
    job = Job.getInstance(conf, "Your job name");

    job.setJarByClass(WebFinderDriver.class);

    if (args.length < 2) {
      return 1;
    }
    if (args.length > 2) {// set max depth and pass parameter to mapper
      conf.setInt("maxDepth", Integer.parseInt(args[2]));
    }

    job.setMapperClass(WebFinderMapper.class);
    job.setMapOutputKeyClass(Text.class);
    job.setMapOutputValueClass(Text.class);

    job.setReducerClass(WebFinderReducer.class);

    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(List.class);

    job.setInputFormatClass(TextInputFormat.class);
    job.setOutputFormatClass(TextOutputFormat.class);

    FileInputFormat.setInputPaths(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));

    job.waitForCompletion(true);
    return 0;
  }
}
