FROM openjdk:17
VOLUME /tmp
COPY build/libs/simple-ecommerce-0.0.1-SNAPSHOT.jar  spring-app.jar
ENTRYPOINT ["java","-jar","/spring-app.jar"]