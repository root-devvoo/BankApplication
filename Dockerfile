FROM FROM openjdk:11-jdk
ARG JAR_FILE=build/libs/bank-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} bankapp.jar
EXPOSE 9090
ENTRYPOINT ["java","-jar","/bankapp.jar"]