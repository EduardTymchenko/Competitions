#FROM openjdk:8-jre
#COPY ./mongo-test.jar ./usr/mongo-test.jar
#EXPOSE 8080
#RUN sh -c 'touch /usr/mongo-test.jar'
#ENTRYPOINT ["java","-jar","/usr/mongo-test.jar"]

FROM  openjdk:8-jre
ADD target/competitions-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
CMD java -jar competitions-0.0.1-SNAPSHOT.jar