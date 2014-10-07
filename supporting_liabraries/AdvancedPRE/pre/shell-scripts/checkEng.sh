#!/bin/ksh
#
#$Revision: 2382 $
#
SCHEDULER_HOME=@schedulerhome
count=`ps -ef -opid,ppid | grep -i \`cat $SCHEDULER_HOME/engine.pid\` | wc -l`
if [ ! -f "$SCHEDULER_HOME/engine.pid" ]; then
   echo "Manually check for the process if its running..."
   echo "Process Id not found."
elif [  $count = 2 ]; then 
	echo "Engine is ON. Process Id `cat $SCHEDULER_HOME/engine.pid`"
elif [ $count = 1 ]; then 
   echo "Engine is ON but in an unhealthy state. Action: Kill and Start."
else
   echo "Unable to get the Status of the Engine"
fi;
