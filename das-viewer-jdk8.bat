@echo off
set CLASSPATH=
for %%i in ("%~dp0build\libs\*.jar") do call :addcp %%i
java -Dfile.encoding=ISO-8859-15 dem2k.DasViewer %*
goto ende
:addcp
set CLASSPATH=%1;%CLASSPATH%
:ende
