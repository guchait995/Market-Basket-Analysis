package org;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class SupportMapper extends Mapper<LongWritable,Text, Text, DoubleWritable> {
	private int total;
	Text outkey= new Text();
	DoubleWritable support=new DoubleWritable();
	
	@Override
	protected void setup(Mapper<LongWritable, Text, Text, DoubleWritable>.Context context)
			throws IOException, InterruptedException {
		Path [] path=context.getLocalCacheFiles();
		for (Path path2 : path) {
			String strp=path2.toString();
			Scanner sc= new Scanner(new File(strp));
			while(sc.hasNextLine())
			{
				total=Integer.parseInt(sc.nextLine().trim());
			}
		}
	}
	@Override
	protected void map(LongWritable key, Text value,Context context)
			throws IOException, InterruptedException {
		String record=value.toString();
		String fields[]=record.split("\\t");
		double out=0;
		out=(double)Double.parseDouble(fields[1])/total;
		outkey.set(fields[0]);
		support.set(out);
		context.write(outkey, support);
	}
}
