FROM gradle:8.7.0-jdk-alpine as builder
USER root
RUN update-ca-certificates

FROM builder as build

COPY  . /workspace

RUN chown -R gradle:gradle /workspace/* && chown -R gradle:gradle /workspace/.
USER gradle
RUN cd /workspace && gradle build

FROM amazoncorretto:17.0.11
WORKDIR /usr/src/app
COPY --from=build /workspace/app/build/libs/app.jar ./app.jar
COPY --from=build /workspace/app/build/runtimeDependencies/applicationinsights-agent*.jar applicationinsights-agent.jar
COPY run.sh /usr/src/app/run.sh
###COPY mssql-jdbc-fatjar-1.0-SNAPSHOT-all.jar /usr/src/app/mssql.jar
###COPY mssql-jdbc-fatjar-v34.jar /usr/src/app/mssql-jdbc-fatjar-v34.jar
CMD  ["sh", "-c", "./run.sh"]
EXPOSE 8080/tcp
