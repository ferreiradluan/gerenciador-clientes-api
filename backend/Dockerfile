# Use OpenJDK 17 como base
FROM openjdk:17-jdk-slim

# Definir diretório de trabalho
WORKDIR /app

# Copiar o arquivo pom.xml primeiro para aproveitar o cache do Docker
COPY pom.xml .

# Copiar o wrapper do Maven
COPY mvnw .
COPY .mvn .mvn

# Dar permissão de execução ao wrapper do Maven
RUN chmod +x mvnw

# Baixar dependências (isso será cacheado se o pom.xml não mudar)
RUN ./mvnw dependency:go-offline -B

# Copiar o código fonte
COPY src src

# Compilar a aplicação
RUN ./mvnw clean package -DskipTests

# Expor a porta da aplicação
EXPOSE 8080

# Comando para executar a aplicação
CMD ["java", "-jar", "target/gerenciador-clientes-api-0.0.1-SNAPSHOT.jar"]
