#!/bin/sh

HOME=`pwd`

classpath=./
for i in `ls -1 $HOME/lib/*.*`; do classpath=$classpath:$i ; done;

echo $classpath

nohup java -classpath $classpath com.stgmastek.monitor.comm.main.StartMonitorCommunication -startup $HOME/log4j.properties &

