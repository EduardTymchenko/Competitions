FROM  openjdk:8-jre
ADD target/competitions-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
ENTRYPOINT ["java","-jar","/competitions-0.0.1-SNAPSHOT.jar"]