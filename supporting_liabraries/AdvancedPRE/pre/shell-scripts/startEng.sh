#!/bin/ksh
#
#$Revision: 2962 $
#
# Note: If Java is not set in the profile classpath then please add the same in classpath here.
#
# Refer to the 2 properties namely javaruntimevmargs & javaextraclasspath in pr.properties for setting
# java virtual machine arguments and for setting extra classpath settings.

SCHEDULER_HOME=@schedulerhome
#Add your specific libraries that are not inside $SCHEDULER_HOME/lib/folder below.
CLASSPATH="$SCHEDULER_HOME/@jarfile"
for i in `ls -1 $SCHEDULER_HOME/lib/*`; do CLASSPATH=$CLASSPATH:$i; done;
echo CLASSPATH=$CLASSPATH
echo 
if [ ! -f "$SCHEDULER_HOME/engine.pid" ]; then
	if [ -f "$SCHEDULER_HOME/console.out" ]; then
	   rm -f $SCHEDULER_HOME/console.out
	fi;
	if [ -f "$SCHEDULER_HOME/error.out" ]; then
	   rm -f $SCHEDULER_HOME/error.out
	fi;
   echo "Starting the Engine..."
   nohup java -Dpre.home=$SCHEDULER_HOME -classpath $CLASSPATH stg.pr.engine.startstop.CStartEngine $SCHEDULER_HOME/resources/prinit.properties $SCHEDULER_HOME/resources/log4j.properties >$SCHEDULER_HOME/console.out &
   echo $! > $SCHEDULER_HOME/engine.pid
   echo "Engine started. Process Id `cat $SCHEDULER_HOME/engine.pid`"
elif [ `ps -fp \`cat $SCHEDULER_HOME/engine.pid\` | wc -l` = 2 ]; then 
	echo "Engine already started. Process Id: `cat $SCHEDULER_HOME/engine.pid`"
elif [ `ps -fp \`cat $SCHEDULER_HOME/engine.pid\` | wc -l` != 2 ]; then 
	if [ -f "$SCHEDULER_HOME/console.out" ]; then
	   rm -f $SCHEDULER_HOME/console.out
	fi;
	if [ -f "$SCHEDULER_HOME/error.out" ]; then
	   rm -f $SCHEDULER_HOME/error.out
	fi;
   echo "Starting the Engine..."
   nohup java -Dpre.home=$SCHEDULER_HOME -classpath $CLASSPATH stg.pr.engine.startstop.CStartEngine $SCHEDULER_HOME/resources/prinit.properties $SCHEDULER_HOME/resources/log4j.properties >$SCHEDULER_HOME/console.out &
   echo $! > $SCHEDULER_HOME/engine.pid
   echo "Engine started. Process Id `cat $SCHEDULER_HOME/engine.pid`"
fi;
