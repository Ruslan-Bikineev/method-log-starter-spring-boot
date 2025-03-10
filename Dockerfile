FROM maven:3.9.9-amazoncorretto-17 AS builder
WORKDIR /app
COPY . /app/.
RUN  mvn -f ./method-logger/pom.xml dependency:resolve install -Dmaven.test.skip=true \
    && mvn -f ./method-log-starter/pom.xml dependency:resolve install -Dmaven.test.skip=true \
    && mvn -f ./task-service/pom.xml -B dependency:resolve package -Dmaven.test.skip=true

FROM amazoncorretto:17-al2-native-jdk
WORKDIR /app
COPY --from=builder /app/task-service/target/*.jar /app/task-service.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","task-service.jar"]