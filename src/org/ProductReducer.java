package org;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class ProductReducer extends Reducer<Text, IntWritable, Text, IntWritable>{
	IntWritable outval=new IntWritable(1);
		
@Override
protected void reduce(Text key, Iterable<IntWritable> value,Context context) throws IOException, InterruptedException {
	int sum=0;
	for (IntWritable intWritable : value) {
		sum++;
	}
	if(sum>0){
	outval.set(sum);
	context.write(key, outval);
	}
	
	
}

}
