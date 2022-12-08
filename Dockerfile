FROM openjdk:8
EXPOSE 8080
ADD target/user-app-0.0.1-SNAPSHOT.jar user-app-0.0.1-SNAPSHOT.jar 
ENTRYPOINT ["java" ,"-jar", "docker-jenkins.jar"]