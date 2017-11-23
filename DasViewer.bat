@echo off
set CLASSPATH=%~dp0out\production\classes;%~dp0out\production\resources
@rem for %%i in ("%~dp0target\dependency\*.jar") do call :addcp %%i
java -Dfile.encoding=ISO-8859-15 dem2k.DasViewer %*
goto ende
:addcp
set CLASSPATH=%1;%CLASSPATH%
:ende
