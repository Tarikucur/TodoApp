FROM openjdk:17-jdk
RUN useradd -r -u 1001 -m -g root app
USER app
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]