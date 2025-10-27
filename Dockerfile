# Use a small JRE image to run the built jar
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Add jar (we will create jar in pipeline)
COPY target/java-docker-app-1.0-SNAPSHOT.jar app.jar

# Default command to run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]
