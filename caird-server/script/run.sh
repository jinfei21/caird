#!/bin/bash

work_dir=`dirname $0`
cd ${work_dir}

HOST_IP=`ifconfig eth0 |grep "inet addr"| cut -f 2 -d ":"|cut -f 1 -d " "`

JAVA_OPTS="-Xmx384m \
		-Djava.library.path=/opt/app/native/lib" 

		
JMS_OPTS="-Dcom.sun.management.jmxremote=true \
-Dcom.sun.management.jmxremote.port=2999 \
-Dcom.sun.management.jmxremote.authenticate=false \
-Dcom.sun.management.jmxremote.ssl=false \
-Djava.rmi.server.hostname=$HOST_IP"

MAIN_CLASS=com.ctrip.bf.useraction.writer.UserActionWriter

ua_pid=${recv_pid-"${work_dir}/logs/ua.pid"}
step=${step-"${work_dir}/logs/step"}
RETVAL=0
PID=0

CP=conf

for jar in `ls lib/*.jar`
do
   CP=$CP:$jar
done

start(){
    printf 'Starting the server\n'
	
    PID=`ps x|grep ${MAIN_CLASS}|grep -v grep|grep ${MAIN_CLASS}|awk '{print $1}'`

	echo "start" > "$step"
     if [ "${PID}" != "" ]; then
	     printf 'RCFileRecovery is runing!PID=%d\n' "${PID}"
		 return 0
     else
	     nohup java $JAVA_OPTS $JMX_OPTS -cp $CP $MAIN_CLASS > /dev/null 2>1 &
	    #java $JAVA_OPTS $JMX_OPTS -cp $CP $MAIN_CLASS 
	     RETVAL=$?
	     [ $RETVAL = 0 ] && echo $! > ${ua_pid} && echo "ok" || echo "fail"
	     return $RETVAL
    fi
	
}


stop(){

   printf 'Stopping the server\n'
   
   PID=`ps x|grep ${MAIN_CLASS}|grep -v grep|grep ${MAIN_CLASS}|awk '{print $1}'`
   #PID=`cat ${ua_pid}`
   
   
   echo "stop" > "$step"
   if [ "${PID}" != "" ]; then
   
     kill ${PID}
     if [ $? -eq 0 ]; then
	    printf 'Done\n'
     else
	    printf 'The server could not stopped\n'
		RETVAL=1
     fi
   else
	    printf 'The server could not stopped\n'
		RETVAL=1
   fi
}

status(){
	PID=`ps x|grep ${MAIN_CLASS}|grep -v grep|grep ${MAIN_CLASS}|awk '{print $1}'`
	
	if [ "${PID}" != "" ]; then
		printf 'server running process:PID=%d' "${PID}"
		RETVAL=0
	else
		printf 'No process found stopped !\n'
		
		var_step=`cat "$step"`
		if [ "$var_step" == "start" ]; then
			RETVAL=1
		else
			RETVAL=0
		fi
	fi
}

case "$1" in
  start)
      start $*
	;;

  stop)
      stop $*
	;;
  status)
	  status $*
	 ;;
   restart)
      stop
      start
      ;;	 
  *)
      echo "Usage: $0 {start|stop|restart|status}"
	;;
esac

exit $RETVAL
