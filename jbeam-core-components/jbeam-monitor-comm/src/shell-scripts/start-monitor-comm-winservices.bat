@echo off
setlocal ENABLEDELAYEDEXPANSION

set CURRENT_DIR=%cd%

rem change the below line to make sure that the directory is hard coded and can be called from any where.
set HOME=%CURRENT_DIR%

rem set "JAVA_HOME=<path>"

rem Include the path of the JDK installed.
if not %JAVA_HOME% == "" goto gotJava
if not defined JAVA_HOME  (
   echo Edit this batch file and set the appropriate JAVA_HOME to JDK 1.6 installed on this machine
   goto :end
)
echo "JAVA_HOME is not set. Please set it either in this shell script or in environment variables."
goto end


:gotJava
set WTITLE=Monitor-comm
set classpath=
set classpath=%HOME%/lib/*;%classpath%
rem Move all the JAR files into lib directory.
rem for %%i in (%HOME%\lib\*.jar); do set classpath=%%i;!classpath!

echo 
echo CLASSPATH=%classpath%
echo
set HOME=%HOME:\=/%
echo %HOME%
rem start "%WTITLE%" 
%JAVA_HOME%\bin\java.exe  -classpath %classpath% com.stgmastek.monitor.comm.main.StartMonitorCommunication -startup %HOME%/log4j.properties 

:end