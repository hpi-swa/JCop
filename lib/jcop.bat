@echo off
SETLOCAL ENABLEEXTENSIONS
Set command=
Set tmp=
Set prefix=java -classpath "%JCOP_HOME%\aspectjtools.jar";

:PARSE
Set current=%1
IF "%current%"=="-classpath" GoTo :ADD_CP_VAL
IF "%current:~0,2%"=="-D" GOTO :ADD_VM_OPTION
GoTo :ADD_COMMAND

:ADD_VM_OPTION
SET vmOpts=%vmo% %1=%2
Shift
Shift
GOTO :PARSE

:ADD_COMMAND
Set command=%command% %1
IF "%2"=="" GOTO :RUN
Shift
GOTO :PARSE

:ADD_CP_VAL
Shift
Set prefix=%prefix%%1
Shift
GoTo :PARSE


:RUN
%prefix% %vmOpts% %command%

