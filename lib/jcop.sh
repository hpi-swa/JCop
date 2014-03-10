
#stripped the classpathaddition from their //
#CLASSPATHADDITION=$(echo "$JCOP_HOME/aspectjweaver.jar:$JCOP_HOME/aspectjrt.jar:$JCOP_HOME/aspectjtools.jar:bin" | sed -e "s_//_/_g" )


prefix='java -classpath "%JCOP_HOME%\aspectjtools.jar"':
command=""
vmOpts=""

for PARAM in $@
do
  if [ "$PARAM" = "-classpath" ]; then
    shift 
	prefix=$prefix$1
	shift
  fi
  if [ "$PARAM:0:2" = "-D" ]; then
	vmOpts=$vmOpts $1=$2 
	shift shift
  else 
    command=$command$1
	shift  
  fi
done

$prefix $vmOpts $command
