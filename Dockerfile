FROM amazoncorretto:11-alpine-jdk
COPY target/retoCP2024-0.0.1.jar /app/retoCP2024-0.0.1.jar
WORKDIR /app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "retoCP2024-0.0.1.jar"]