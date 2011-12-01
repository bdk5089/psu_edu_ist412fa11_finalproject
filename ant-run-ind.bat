@echo off

REM set Java Home Directory (not the bin subdirectory)
REM Both JDK and JRE should be subdirectories of JAVA_HOME
set JAVA_HOME=C:\Program Files (x86)\Java\jdk1.6.0_24\bin
ECHO JAVA_HOME=%JAVA_HOME%

REM set Ant Home Directory (where you decompressed Ant)
set ANT_HOME=C:\Java\apache-ant-1.8.2
ECHO ANT_HOME=%ANT_HOME%

REM Add the bin directory of the Ant Home directory to your path
set PATH=%PATH%;%ANT_HOME%\bin
echo PATH=%PATH%

REM Run Ant
set target=run-ind
set script=build.xml
ant -f %script% %target%