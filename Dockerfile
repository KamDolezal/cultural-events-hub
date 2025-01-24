# official JDK 21 base image for building
FROM eclipse-temurin:21-jdk AS builder

# working directory inside the Docker container
WORKDIR /app

# copy of maven configuration (pom.xml) and dependencies
COPY . .

# Maven build
RUN ./mvnw clean package -DskipTests

# container based on a lighter JRE image
FROM eclipse-temurin:21-jre

# working directory
WORKDIR /app

# copy of the generated JAR from the builder
COPY --from=builder /app/target/culturaleventshub-0.0.1-SNAPSHOT.jar app.jar

# Clean up unnecessary files after copying the jar
RUN rm -rf /root/.m2 /app/target /app/src

# expose port (for running Spring Boot application)
EXPOSE 8080

# command to launch the application
ENTRYPOINT ["java", "-jar", "app.jar"]