FROM openjdk:17

WORKDIR /app

# Copy the source code to the container
COPY gradle gradle
COPY gradlew .
COPY build.gradle .
COPY settings.gradle .
COPY src src

# Install required package to avoid xargs error.
RUN microdnf install findutils

# Fix gradlew permission.
RUN chmod +x gradlew

# Convert gradlew to use Unix-style line endings.
RUN microdnf install dos2unix
RUN dos2unix gradlew

# Build application and generate the jar file
RUN ./gradlew bootJar

ENTRYPOINT ["java","-jar","build/libs/simple-ecommerce-0.0.1-SNAPSHOT.jar"]