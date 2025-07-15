# Estágio 1: Build da aplicação com Maven usando JDK 8
FROM maven:3.8-jdk-8 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean install

# Estágio 2: Criação da imagem final, leve e otimizada com JRE 8
FROM openjdk:8-jre-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]