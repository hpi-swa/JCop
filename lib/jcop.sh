#!/bin/bash

prefix='java -classpath "%JCOP_HOME%\aspectjtools.jar"':
command=""
vmOpts=""

for PARAM in $@
do
  if [ "$PARAM" = "-classpath" ]; then
	shift
	prefix=$prefix$1
	shift
  elif [ "$PARAM:0:2" = "-D" ]; then
	vmOpts=$vmOpts $1=$2
	shift
    shift
  else
    command=$command$1
	if [ -n "$PARAM" ]; then
        shift
    fi
  fi
done

$prefix $vmOpts $command
