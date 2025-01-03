FROM openjdk:17-jdk-slim
WORKDIR /app
COPY . .
RUN chmod +x gradlew
RUN ./gradlew build -x test
ARG JAR_FILE=build/libs/accountms.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]