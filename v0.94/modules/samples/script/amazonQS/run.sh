#!/bin/sh

export AXIS2_HOME=../..
AXIS2_CLASSPATH=$AXIS2_CLASSPATH:amazonQS.jar
for f in $AXIS2_HOME/lib/*.jar
do
  AXIS2_CLASSPATH=$AXIS2_CLASSPATH:$f
done
export AXIS2_CLASSPATH
echo classpath: $AXIS2_CLASSPATH
java -classpath $AXIS2_CLASSPATH -Daxis2.home=$AXIS2_HOME sample.amazon.amazonSimpleQueueService.RunGUICQ &
java -classpath $AXIS2_CLASSPATH -Daxis2.home=$AXIS2_HOME sample.amazon.amazonSimpleQueueService.RunGUIRQ &


