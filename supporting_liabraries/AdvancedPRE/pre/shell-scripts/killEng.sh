#!/bin/ksh
#
#$Revision: 2382 $
#
SCHEDULER_HOME=@schedulerhome
if [ ! -f "$SCHEDULER_HOME/engine.pid" ]; then
        echo "Process Id not found"
else
   ppid=`cat $SCHEDULER_HOME/engine.pid`
   if [ `ps -ef -o pid,ppid | grep -i $ppid | wc -l` > 0 ]; then
      for child in `ps -ef -opid,ppid | grep -i $ppid |
       awk '{ if ($1 == myppid) print $1;
              if ($2 == myppid) print $1;
            }'`
      do
          echo "Killing process $child"
          kill -9 $child
       done
       echo "PRE killed."
   else
       echo "Engine not started."
   fi;
fi;
