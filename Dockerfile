FROM openjdk:17-jdk-slim

LABEL maintainer="emre@emreozbarut.com"
LABEL version="v1.0.0"
LABEL description="Todo App Backend Service"

# Set working directory
WORKDIR /app

# Copy dependencies
COPY target/todo-app-backend-*.jar app.jar

# Expose ports
EXPOSE 8080
EXPOSE 8081

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]