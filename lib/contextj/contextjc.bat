@echo off
SET clp=

IF "%1"=="-classpath" GOTO SET_OPTIONS
SET compilation_unit=%*

IF "%compilation_unit"=="" GOTO SHOW_OPTIONS
GOTO RUN

:SET_OPTIONS
IF "%3"=="" GOTO RUN 
SET clp=%clp%%2;
SET compilation_unit=%3
SHIFT
GOTO SET_OPTIONS

:SHOW_OPTIONS
java -jar %JCOP_HOME%\jcop.jar
GOTO END

:RUN

java -jar %JCOP_HOME%\jcop.jar -classpath "%clp%.;%JCOP_HOME%\jcop.jar" -debug %compilation_unit% 
 
SET clp=
SET compilation_unit=

:END
