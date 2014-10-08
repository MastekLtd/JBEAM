#!/bin/ksh
#
#$Revision: 2382 $
#
SCHEDULER_HOME=@schedulerhome
ppid=`cat $SCHEDULER_HOME/engine.pid`
for child in `ps -ef -o pid,ppid | grep -i $ppid | awk '{ if ($2==gpid) {print $1} }' gpid=$ppid`
do
   prstat -c -a -p $child  -L -R -v -P 0,1,2,3
done;
