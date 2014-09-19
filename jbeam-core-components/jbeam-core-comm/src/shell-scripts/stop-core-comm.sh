#!/bin/sh

HOME=`pwd`

classpath=./
for i in `ls -1 $HOME/lib/*.*`; do classpath=$classpath:$i ; done;

echo $classpath

java -classpath $classpath com.stgmastek.core.comm.main.StartCoreCommunication -shutdown $HOME/log4j.properties

