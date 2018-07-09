package org;

import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class ProductDriver {
public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
	Configuration configuration=new Configuration();
	//job generates the total number of times particular combinations exist
	Job job=Job.getInstance(configuration,"Combination_Counter");
	job.setJarByClass(ProductDriver.class);
	job.setReducerClass(ProductReducer.class);
	//job.setNumReduceTasks(0);
	job.setMapperClass(ProductMapper.class);
	job.setOutputKeyClass(Text.class);
	job.setOutputValueClass(IntWritable.class);
	FileInputFormat.addInputPath(job, new Path(args[0]));//projectin//args[0]
	FileOutputFormat.setOutputPath(job, new Path(args[1]));//combicount//args[1]
	job.waitForCompletion(true);
	//.................first job completes
	//job2 counts the total number of records
	Job job2= Job.getInstance(configuration,"Total_Transcation_Counter");
	job2.setJarByClass(ProductDriver.class);
	job2.setMapperClass(CountMapper.class);
	job2.setReducerClass(CountReducer.class);
	job2.setMapOutputKeyClass(Text.class);
	job2.setMapOutputValueClass(IntWritable.class);
	job2.setOutputKeyClass(NullWritable.class);
	job2.setOutputValueClass(IntWritable.class);
	FileInputFormat.addInputPath(job2, new Path(args[0]));//projectin//args[0]
	FileOutputFormat.setOutputPath(job2, new Path(args[2]));//combiout2//args[2]
	job2.waitForCompletion(true);
	//second job completes
	//job3 generates the support for each itemset
	Job job3=Job.getInstance(configuration,"Support_Counter");
	Path path= new Path(args[2]+"/part-r-00000");//combiout2//args[1]
	URI uri=path.toUri();
	job3.addCacheFile(uri);
	job3.setJarByClass(ProductDriver.class);
	job3.setMapperClass(SupportMapper.class);
	job3.setOutputKeyClass(Text.class);
	job3.setOutputValueClass(DoubleWritable.class);
	FileInputFormat.addInputPath(job3, new Path(args[1]));//combicount//args[1]
	FileOutputFormat.setOutputPath(job3, new Path(args[3]));//support//args[3]
	job3.waitForCompletion(true);
	//third job completes
	//generates all associations
	Job job4= Job.getInstance(configuration,"Asscociation_Counter");
	job4.setMapperClass(AssociationMapper.class);
	job4.setReducerClass(AssociationReducer.class);
	job4.setJarByClass(ProductDriver.class);
	job4.setOutputKeyClass(Text.class);
	job4.setOutputValueClass(IntWritable.class);
	FileInputFormat.addInputPath(job4, new Path(args[1]));//combicount//args[1]
	FileOutputFormat.setOutputPath(job4, new Path(args[4]));//association//args[4]
	job4.waitForCompletion(true);
	
	//job 5 for confidence and lift
	Job job5=Job.getInstance(configuration,"Confidence_Lift_Counter");
	Path path4= new Path(args[3]+"/part-r-00000");//outdir3(support)
	URI uri4=path4.toUri();
	job5.addCacheFile(uri4);
	job5.setJarByClass(ProductDriver.class);
	job5.setMapperClass(ConfidenceMapper.class);
	job5.setOutputKeyClass(Text.class);
	job5.setOutputValueClass(Text.class);
	FileInputFormat.addInputPath(job5, new Path(args[4]));//indir2(association)
	FileOutputFormat.setOutputPath(job5, new Path(args[5]));//outdir4(confidenceLift)
	job5.waitForCompletion(true);
	
}
}
