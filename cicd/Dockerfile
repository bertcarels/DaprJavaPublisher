FROM jfrog-platform-artifactory-nginx.jfrog-platform/kab-docker-remote/gradle:8.7.0-jdk-alpine as builder
USER root
RUN update-ca-certificates

FROM builder as build

COPY --from=src . /workspace

RUN chown -R gradle:gradle /workspace/* && chown -R gradle:gradle /workspace/.
USER gradle
RUN cd /workspace && gradle printVersion && gradle build && gradle artifactoryPublish
RUN gradle sonar -Dsonar.token=sqa_e72746626a781f6c9f08496eee930d299057ff6e

FROM jfrog-platform-artifactory-nginx.jfrog-platform/kab-docker-remote/amazoncorretto:17.0.11
WORKDIR /usr/src/app
COPY --from=build /workspace/app/build/libs/app.jar ./app.jar
COPY --from=build /workspace/app/build/runtimeDependencies/applicationinsights-agent*.jar applicationinsights-agent.jar
COPY run.sh /usr/src/app/run.sh
CMD  ["sh", "-c", "./run.sh"]
EXPOSE 8080/tcp
