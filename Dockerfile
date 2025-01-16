FROM amazoncorretto:17
WORKDIR /app
COPY build/libs/accountms.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]