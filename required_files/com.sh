user_input_dir=$1
user_output_directory=$2
hadoop fs -rm -r /user/indir
hadoop fs -mkdir /user/indir
hadoop fs -put $user_input_dir /user/indir
hadoop fs -rm -r /user/outdir*
hadoop jar productprob.jar org.ProductDriver /user/indir /user/outdir /user/outdir1 /user/outdir2 /user/outdir3 /user/outdir4
rm -r $2
mkdir $2
hadoop fs -get /user/outdir4/part-r-00000 $2
mv $2/part-r-00000 $2/output.txt
echo "Output File created at :$2/output.txt"
