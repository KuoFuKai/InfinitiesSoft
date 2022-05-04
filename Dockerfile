FROM adoptopenjdk/openjdk11:x86_64-alpine-jdk-11.0.11_9-slim
LABEL maintainer="kevin08062006@gmail.com"
EXPOSE 8080
ADD target/infinitiessoft_demo_project.jar infinitiessoft_demo_project.jar
ENTRYPOINT ["java","-jar","/infinitiessoft_demo_project.jar"]