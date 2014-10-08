@echo off
setlocal ENABLEDELAYEDEXPANSION
rem Windows batch file that helps to boot the engine.
rem Provided without warranty.
rem 
rem $Revision:  $

rem The following path should use the DOS style example if the directory installed 
rem is in C:\someclient\PRE28 then this the following entry should be C:\someclient\PRE28
rem SCHEDULER_HOME=<replace this with the install directory>
set SCHEDULER_HOME=D:\kedar\Mastek\workspace\AdvancedPRE

rem Include the path of the JDK installed.
set JAVA_HOME=C:\Program Files\Java\jdk1.6.0_16
set WTITLE=PRE28-Bounce

rem replace the below with the user id if the windows login id is more than 15 characters.
set USERID=%USERNAME%

if not defined JAVA_HOME  (
   echo Edit this batch file and set the appropriate JAVA_HOME to JDK 1.6 installed on this machine
   goto :EXIT
)

set classpath=
rem Move all the JAR files into lib directory.
for %%i in (%SCHEDULER_HOME%\lib\*.jar); do set classpath=%%i;!classpath!

echo 
echo CLASSPATH=%classpath%
echo
set SCHEDULER_HOME=%SCHEDULER_HOME:\=/%
echo %SCHEDULER_HOME%
start "%WTITLE%" "%JAVA_HOME%\bin\java.exe" -Duserid=%USERID% -Djobid="1.001" -Dreqtype="GENERAL" -Djava.compiler=NONE -classpath %CLASSPATH% admin.CRebootEngine %SCHEDULER_HOME%/resources/prinit.properties 

:EXIT