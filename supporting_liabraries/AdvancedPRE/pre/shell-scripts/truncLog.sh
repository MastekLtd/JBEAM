#!/bin/ksh
#
#$Revision: 2382 $
#
SCHEDULER_HOME=@schedulerhome
LOG_FILE=console.out
if [ ! -f "$SCHEDULER_HOME/engine.pid" ]; then
	if [ -f "$SCHEDULER_HOME/$LOG_FILE" ]; then
	   rm -f $SCHEDULER_HOME/$LOG_FILE
	   echo "Engine not started...Removing the existing log file $SCHEDULER_HOME/$LOG_FILE"
	fi;
elif [ `ps -fp \`cat $SCHEDULER_HOME/engine.pid\` | wc -l` = 2 ]; then 
	cat /dev/null > $SCHEDULER_HOME/$LOG_FILE
	echo "Engine in running mode. Truncated the $SCHEDULER_HOME/$LOG_FILE"
elif [ `ps -fp \`cat $SCHEDULER_HOME/engine.pid\` | wc -l` != 2 ]; then 
	if [ -f "$SCHEDULER_HOME/$LOG_FILE" ]; then
	   rm -f $SCHEDULER_HOME/$LOG_FILE
	   echo "Engine is stopped/killed...Removing the existing log file $SCHEDULER_HOME/$LOG_FILE"
	fi;
fi;
