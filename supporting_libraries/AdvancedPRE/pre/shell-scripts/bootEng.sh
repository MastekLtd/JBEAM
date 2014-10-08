#!/bin/ksh
#
#$Revision: 2962 $
#
SCHEDULER_HOME=@schedulerhome
#Note if the os user id is too big the request may not get inserted into the queue table. It is then recommended
#to change the user id to a fix value.
USERID=`whoami`
#Add your specific libraries that are not inside $SCHEDULER_HOME/lib/folder below.
CLASSPATH=$SCHEDULER_HOME/@jarfile
for i in `ls -1 $SCHEDULER_HOME/lib/*`; do CLASSPATH=$CLASSPATH:$i; done;
echo CLASSPATH=$CLASSPATH
echo

java -Duserid=$USERID -Djobid="1.001" -Dreqtype="GENERAL" -Djava.compiler=NONE -classpath $CLASSPATH admin.CRebootEngine $SCHEDULER_HOME/resources/prinit.properties  $USERID
