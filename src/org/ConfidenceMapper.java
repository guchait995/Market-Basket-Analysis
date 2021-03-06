package org;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class ConfidenceMapper extends Mapper<LongWritable,Text, Text, Text>{
	
HashMap<String, Double> supportKeys=new HashMap<String, Double>();	
private Text ConfAndLift=new Text();
@Override
protected void setup(Context context)
		throws IOException, InterruptedException {
	// TODO Auto-generated method stub
	//super.setup(context);
	Path [] path=context.getLocalCacheFiles();
	for (Path path2 : path) {
		String strp=path2.toString();
		Scanner sc= new Scanner(new File(strp));
		while(sc.hasNextLine())
		{
			String record = sc.nextLine();
			String[] fields=record.split("\\t");
			//HashMap<String, Double> hashMap=new HashMap<String, Double>();
			if(fields.length>=2){
			supportKeys.put(fields[0],Double.parseDouble(fields[1]));
			}
			//supportKeys.add(hashMap);
			//total=Integer.parseInt(sc.nextLine().trim());
		}
	}
}
@Override
	protected void map(LongWritable key, Text value,Context context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		//super.map(key, value, context);
		String record=value.toString();
		String[] recordKey=record.split("\\t");
		if(recordKey.length>=2){
		String[] fields=recordKey[0].split("->");
		Double supportA=0.0;Double supportB=0.0;Double supportAB=0.0;Double confidenceAB=0.0;Double liftAB=0.0;
		if(fields.length>=2){
		if(supportKeys.get(fields[0])!=null)
		{supportA=supportKeys.get(fields[0]);
		
		}
		if(supportKeys.get(fields[1])!=null)
		{
		supportB=supportKeys.get(fields[1]);
		}
		String[] sortarray=(fields[0]+fields[1]).split(",");
		sortarray=bubbleSort(sortarray);
		String sorted= new String();
		for (String string : sortarray) {
			sorted+=string+",";
		}
		if(supportKeys.get(sorted)!=null)
		{
		supportAB=supportKeys.get(sorted);
		}
		if(supportB!=0.0)
		{
		confidenceAB=supportAB/supportB;
		}
		if((supportA*supportB)!=0.0)
		{
		liftAB=supportAB/(supportA*supportB);
		}
		if(confidenceAB>0.0 && liftAB>0.0){
		DecimalFormat decimalFormat=new DecimalFormat("#0.00000");
		
		String confidenceAndLift=String.valueOf(decimalFormat.format(supportA))+
				","+String.valueOf(decimalFormat.format(supportB))+
				","+String.valueOf(decimalFormat.format(supportAB))+
				","+String.valueOf(decimalFormat.format(confidenceAB))+
				","+String.valueOf(decimalFormat.format(liftAB));
		ConfAndLift.set(confidenceAndLift);
		//value.set(fields[0]+" "+fields[1]+",");
		value.set(recordKey[0]);
		context.write(value, ConfAndLift);}
		}
		}
	}
public String[] bubbleSort(String[] stringArray) {
	
	int k=0;
	for (String string : stringArray) {
		stringArray[k]=string;
		k++;
	}
    int n = stringArray.length;
    String temp;
    for (int i = 0; i < n; i++) {
        for (int j = 1; j < (n - i); j++) {
            if (stringArray[j - 1].compareTo( stringArray[j] ) > 0) {
                temp = stringArray[j - 1];
                stringArray[j - 1] = stringArray[j];
                stringArray[j] = temp;
            }

        }
    }
    
    return stringArray;
}
}


//confidence
/*
 * confidence(a->b)=[support(a,b)/support(b)]
 * lift(a->b)=[support(a,b)/{support(a)*support(b)}];
 */