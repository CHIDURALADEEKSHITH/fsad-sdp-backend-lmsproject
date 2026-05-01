# Stage 1: Build the JAR
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run the JAR
FROM eclipse-temurin:21-jre-ubi9-minimal
WORKDIR /app

# Change *.war to *.jar here
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

# Change app.war to app.jar here
ENTRYPOINT ["java","-jar","app.jar"]