FROM openjdk:8
EXPOSE 8080
ADD /target/user-app-docker-image.jar user-app-docker-image.jar
ENTRYPOINT ["java" ,"-jar","/user-app-docker-image.jar"]