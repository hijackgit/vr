#FROM maven:3.6.3-jdk-8
#COPY src /app/src
#COPY pom.xml /app 
#RUN mvn -f app/pom.xml clean package -Dmaven.test.skip 


FROM openjdk:8-jdk-alpine
EXPOSE 8080
ADD target/recrutamentovr-1.0.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]