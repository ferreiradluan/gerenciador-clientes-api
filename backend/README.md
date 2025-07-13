# API Backend - Gerenciador de Clientes

API RESTful robusta para gerenciamento de clientes com autenticação JWT e integração com serviços externos.

## � Tecnologias

- **Java 17** + **Spring Boot 3.1.5**
- **Spring Security** + **JWT Authentication**
- **PostgreSQL** com **JPA/Hibernate**
- **Maven** + **Docker**
- **OpenAPI/Swagger** para documentação

## 🚀 Quick Start

### Pré-requisitos
- Java 17+
- PostgreSQL (local ou Docker)

### Executar
```bash
# Banco via Docker
docker-compose up -d postgres

# Aplicação
./mvnw spring-boot:run
```

### Documentação
- **Swagger:** http://localhost:8080/swagger-ui.html
- **API Docs:** http://localhost:8080/v3/api-docs

## 📋 Principais Endpoints

```http
POST   /api/auth/login           # Autenticação
GET    /api/clientes             # Listar clientes
POST   /api/clientes             # Criar cliente
GET    /api/clientes/{id}        # Buscar por ID
PUT    /api/clientes/{id}        # Atualizar cliente
DELETE /api/clientes/{id}        # Remover cliente
```

## ✅ Funcionalidades

- **CRUD Completo** de clientes
- **Autenticação JWT** com Spring Security
- **Validação de CPF** brasileiro
- **Integração ViaCEP** para endereços
- **Paginação e Filtros** avançados
- **Testes Automatizados** (100% cobertura)

## 🧪 Testes

```bash
./mvnw test                      # Executar todos os testes
./mvnw test-compile test         # Com relatórios
```