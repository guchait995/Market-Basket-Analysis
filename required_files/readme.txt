1) First setup Hadoop Following the steps in the Hadoop Pdf attachhed in reqired files:
..............................................................................................
download hadoop from url :                                                                    |
https://dist.apache.org/repos/dist/release/hadoop/common/hadoop-2.7.6/hadoop-2.7.6.tar.gz     |
Or  (Linux)                                                                                   |(Must Required to run the project)
wget https://dist.apache.org/repos/dist/release/hadoop/common/hadoop-2.7.6/hadoop-2.7.6.tar.gz|
...........................................................................................
Pig Download Link:                                                                        |
https://archive.apache.org/dist/pig/stable/pig-0.12.1.tar.gz                              |
Or (linux)                                                                                |(OPtional)
wget https://archive.apache.org/dist/pig/stable/pig-0.12.1.tar.gz                         |
...........................................................................................
Hive download link :                                                                      |
https://archive.apache.org/dist/hive/hive-2.0.0/apache-hive-2.0.0-bin.tar.gz              |(Optional)
Or  (Linux)                                                                               |
wget https://archive.apache.org/dist/hive/hive-2.0.0/apache-hive-2.0.0-bin.tar.gz         |
...........................................................................................
Hbase:                                                                                     |
https://archive.apache.org/dist/hbase/2.0.0/hbase-2.0.0-bin.tar.gz                         |(Optional)
Or (Linux)                                                                                 |
wget https://archive.apache.org/dist/hbase/2.0.0/hbase-2.0.0-bin.tar.gz                    |
...........................................................................................|

2) Clone my github directory
3) Add jars to build path
4) Export the project to your_jar_name.jar file
5) Keep all the required files 
	a)com.sh
	b)your_input_file.txt //must be Space separated
	c)jar file
	in Same directory...
6)Open the directory in terminal
7)sh com.sh your_jar_name.jar your_input_file.txt desired_output_folder
