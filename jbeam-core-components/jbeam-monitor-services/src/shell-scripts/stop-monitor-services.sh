#!/bin/sh

HOME=`pwd`

classpath=./
for i in `ls -1 $HOME/lib/*.*`; do classpath=$classpath:$i ; done;

echo $classpath

java -classpath $classpath com.stgmastek.monitor.ws.main.StartMonitorWS -shutdown $HOME/log4j.properties

