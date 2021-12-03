ARG SPIRENT_DOCKER_REPO=artifactory.vwx.spirent.com/dockerhub
FROM ${SPIRENT_DOCKER_REPO}/openjdk:17-jdk

WORKDIR /app

COPY /target/*.jar ./app.jar

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "app.jar" ]