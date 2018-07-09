package org;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class AssociationReducer extends Reducer<Text, IntWritable, Text, IntWritable>{
	IntWritable outval= new IntWritable();
	@Override
	protected void reduce(Text arg0, Iterable<IntWritable> arg1,
			Reducer<Text, IntWritable, Text, IntWritable>.Context context) throws IOException, InterruptedException {
		int sum=0;
		for (IntWritable intWritable : arg1) {
			sum++;
		}
		outval.set(sum);
		context.write(arg0, outval);
	}

}
