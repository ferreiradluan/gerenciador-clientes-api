# Gerenciador de Clientes - Backend

API RESTful desenvolvida em Spring Boot para gerenciamento de clientes.

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.5.3**
- **Spring Data JPA** - Para persistência de dados
- **Spring Security** - Para autenticação e autorização
- **Spring Validation** - Para validação de dados
- **PostgreSQL** - Banco de dados
- **Lombok** - Para reduzir boilerplate code
- **Maven** - Gerenciador de dependências
- **Docker** - Para containerização

## Como Executar

### Opção 1: Usando Docker (Recomendado)

#### Pré-requisitos
- Docker e Docker Compose instalados

#### Executando com Docker

```bash
# Forma mais simples - usando o script
./start.sh

# Ou manualmente
docker compose up --build

# Para rodar em background
docker compose up --build -d
```

A aplicação estará disponível em `http://localhost:8080` e o PostgreSQL em `localhost:5432`.

### Opção 2: Executando Localmente

#### Pré-requisitos
- Java 17 ou superior
- PostgreSQL instalado e configurado

#### Configuração do Banco de Dados

1. Crie um banco de dados PostgreSQL
2. Configure as propriedades no arquivo `src/main/resources/application.properties`:

```properties
spring.application.name=gerenciador-clientes-api

# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/gerenciador_clientes
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# Server Configuration
server.port=8080
```

#### Executando a Aplicação

```bash
# Usando Maven Wrapper (recomendado)
./mvnw spring-boot:run

# Ou usando Maven instalado
mvn spring-boot:run
```

A API estará disponível em: `http://localhost:8080`

### Acessar o container da aplicação
```bash
docker compose exec api bash
```

### Acessar o PostgreSQL
```bash
docker compose exec postgres psql -U postgres -d gerenciador_clientes
```

## Ambiente de Desenvolvimento

Se você quiser desenvolver localmente mas usar apenas o PostgreSQL no Docker:

```bash
# Subir apenas o PostgreSQL
docker compose -f docker-compose.dev.yml up -d

# Executar a aplicação localmente
./mvnw spring-boot:run
```

## Estrutura do Projeto

```
backend/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── br/com/luanferreira/desafio/gerenciador_clientes_api/
│   │   │       └── GerenciadorClientesApiApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── application-docker.properties
│   └── test/
├── Dockerfile
├── docker-compose.yml
├── docker-compose.dev.yml
├── start.sh
└── pom.xml
```

## Endpoints da API

*A documentação dos endpoints será atualizada conforme o desenvolvimento*

### Clientes

- `GET /api/clientes` - Listar todos os clientes
- `GET /api/clientes/{id}` - Buscar cliente por ID
- `POST /api/clientes` - Criar novo cliente
- `PUT /api/clientes/{id}` - Atualizar cliente
- `DELETE /api/clientes/{id}` - Excluir cliente

## Desenvolvimento

### Executando Testes

```bash
./mvnw test
```

### Build da Aplicação

```bash
./mvnw clean package
```

### Executando com Profile de Desenvolvimento

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

## Comandos Úteis do Docker

### Parar os containers
```bash
docker compose down
```

### Parar e remover volumes (limpar dados do banco)
```bash
docker compose down -v
```

### Ver logs da aplicação
```bash
docker compose logs api
```

### Ver logs do banco de dados
```bash
docker compose logs postgres
```

### Rebuildar apenas a aplicação
```bash
docker compose build api
docker compose up api
```

## Configurações Adicionais

### CORS

O CORS está configurado para permitir requisições do frontend Angular (localhost:4200).

### Documentação da API

A documentação da API será implementada usando Swagger/OpenAPI.

## Próximos Passos

- [ ] Implementar entidade Cliente
- [ ] Criar repositório JPA
- [ ] Implementar serviços de negócio
- [ ] Criar controllers REST
- [ ] Configurar Spring Security
- [ ] Implementar validações
- [ ] Adicionar documentação Swagger
- [ ] Implementar testes unitários e de integração
