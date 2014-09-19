#!/bin/sh

HOME=`pwd`

classpath=./
for i in `ls -1 $HOME/lib/*.*`; do classpath=$classpath:$i ; done;

echo $classpath

nohup java -Djbeam-com.stgmastek.core.comm.server.dao.statusdaoimpl=com.stgmastek.core.comm.server.dao.impl.StatusDAO -Djbeam-com.stgmastek.core.comm.server.dao.batchdaoimpl=com.stgmastek.core.comm.server.dao.impl.BatchDAO -classpath $classpath com.stgmastek.core.comm.main.StartCoreCommunication -startup $HOME/log4j.properties &

