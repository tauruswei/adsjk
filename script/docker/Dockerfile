FROM maven:3.8.6-openjdk-8 as build

WORKDIR /opt

ADD . /opt/

RUN mvn clean install -DskipTests

FROM java:8-jre

COPY --from=build /opt/application/target/*-SNAPSHOT.jar /applications/application.jar

ENTRYPOINT ["/bin/sh","-c","java -jar -Dfile.encoding=UTF-8 /applications/application.jar --server.port=8090"]
