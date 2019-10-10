FROM openjdk:11-slim

COPY build/libs/spring-boot-example*jar /app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
