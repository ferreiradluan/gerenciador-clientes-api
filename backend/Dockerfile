# Estágio 1: Build da aplicação com Maven
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
# Executa o build e os testes com o perfil de teste
RUN mvn clean install -Dspring.profiles.active=test

# Estágio 2: Criação da imagem final, leve e otimizada
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# Copia apenas o JAR gerado no estágio de build
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
# Ponto de entrada para executar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
