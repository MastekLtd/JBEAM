@echo off
setlocal ENABLEDELAYEDEXPANSION

set CURRENT_DIR=%cd%

rem change the below line to make sure that the directory is hard coded and can be called from any where.
set HOME=%CURRENT_DIR%

set JAVA_HOME=C:\Java\jdk1.6.0_23

rem Include the path of the JDK installed.
if not %JAVA_HOME% == "" goto gotJava
if not defined JAVA_HOME  (
   echo Edit this batch file and set the appropriate JAVA_HOME to JDK 1.6 installed on this machine
   goto :end
)
echo "JAVA_HOME is not set. Please set it either in this shell script or in environment variables."
goto end


:gotJava
set WTITLE=Core-comm
set classpath=
set classpath=%HOME%/lib/*;%CLASSPATH%
rem Move all the JAR files into lib directory.
rem for %%i in (%HOME%\lib\*.jar); do set classpath=%%i;!classpath!

echo 
echo CLASSPATH=%classpath%
echo
set HOME=%HOME:\=/%
echo %HOME%
start "%WTITLE%" %JAVA_HOME%\bin\java.exe -Djbeam-com.stgmastek.core.comm.server.dao.statusdaoimpl=com.stgmastek.core.comm.server.dao.impl.StatusDAO -Djbeam-com.stgmastek.core.comm.server.dao.batchdaoimpl=com.stgmastek.core.comm.server.dao.impl.BatchDAO  -classpath %classpath% com.stgmastek.core.comm.main.StartCoreCommunication -startup %HOME%/log4j.properties 

:end