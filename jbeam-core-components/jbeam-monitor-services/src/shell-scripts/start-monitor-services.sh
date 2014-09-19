#!/bin/sh

HOME=`pwd`

classpath=./
for i in `ls -1 $HOME/lib/*.*`; do classpath=$classpath:$i ; done;

echo $classpath

nohup java -Djbeam-com.stgmastek.monitor.ws.server.dao.monitordaoimpl=com.stgmastek.monitor.ws.server.dao.impl.MonitorDAO -Djbeam-com.stgmastek.monitor.ws.server.dao.userdaoimpl=com.stgmastek.monitor.ws.server.dao.impl.UserDAO -classpath $classpath com.stgmastek.monitor.ws.main.StartMonitorWS -startup $HOME/log4j.properties &

