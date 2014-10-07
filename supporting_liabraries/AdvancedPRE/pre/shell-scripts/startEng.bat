@echo off
setlocal ENABLEDELAYEDEXPANSION
rem Windows batch file that helps to boot the engine.
rem Provided without warranty.
rem 
rem $Revision:  $

rem The following path should use the DOS style example if the directory installed 
rem is in C:\someclient\PRE28 then this the following entry should be C:\someclient\PRE28
set SCHEDULER_HOME=<replace this with the install directory>


rem Include the path of the JDK installed.
set JAVA_HOME=C:\Program Files\Java\jdk1.6.0_16
set WTITLE=PRE28-Engine


if not defined JAVA_HOME  (
   echo Edit this batch file and set the appropriate JAVA_HOME to JDK 1.6 installed on this machine
   goto :EXIT
)

set classpath=D:\kedar\Mastek\workspace\AdvancedPRE\bin
rem Move all the JAR files into lib directory.
for %%i in (%SCHEDULER_HOME%\lib\*.jar); do set classpath=%%i;!classpath!

echo 
echo CLASSPATH=%classpath%
echo
set SCHEDULER_HOME=%SCHEDULER_HOME:\=/%
echo %SCHEDULER_HOME%
start "%WTITLE%" "%JAVA_HOME%\bin\java.exe"  -Dpre.home=%SCHEDULER_HOME% -classpath %CLASSPATH% stg.pr.engine.startstop.CStartEngine %SCHEDULER_HOME%/resources/prinit.properties %SCHEDULER_HOME%/resources/log4j.properties 

:EXIT
