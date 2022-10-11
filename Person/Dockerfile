FROM openjdk:17

COPY target/Person.jar /app.jar

EXPOSE 8081/tcp

CMD ["/usr/bin/java", "-jar", "/app.jar"]