#!/bin/sh
##### for full network debug add -Djavax.net.debug=all
cd /usr/src/app
if [ {{ .Values.applicationinsights.enabled }} == true ]
then 
   java  -javaagent:"/usr/src/app/applicationinsights-agent.jar" -jar app.jar
else
   java  -jar app.jar
fi  
sleep 3600    