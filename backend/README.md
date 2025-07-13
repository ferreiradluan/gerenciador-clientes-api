# 🎯 Gerenciador de Clientes API

> API RESTful moderna para gerenciamento completo de clientes com autenticação JWT, validações avançadas e integração com serviços externos.

[![Java](https://img.shields.io/badge/Java-17-orange)](https://openjdk.java.net/projects/jdk/17/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.5-brightgreen)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue)](https://www.postgresql.org/)
[![License](https://img.shields.io/badge/License-MIT-green)](LICENSE)

## 🛠️ Stack Tecnológica

- **Backend:** Java 17 + Spring Boot 3.1.5
- **Segurança:** Spring Security + JWT
- **Banco de Dados:** PostgreSQL + JPA/Hibernate  
- **Build:** Maven
- **Deploy:** Docker + Heroku
- **Docs:** OpenAPI 3.0/Swagger
- **Testes:** JUnit 5 + Mockitond - Gerenciador de Clientes

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
- Maven 3.6+

### Executar Localmente
```bash
# 1. Clonar o repositório
git clone https://github.com/ferreiradluan/gerenciador-clientes-api.git
cd gerenciador-clientes-api/backend

# 2. Banco via Docker
docker-compose up -d postgres

# 3. Executar aplicação
./mvnw spring-boot:run
```

### Acessar Documentação
- **Swagger UI:** http://localhost:8080/swagger-ui/index.html
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

## 🚀 Deploy

### Heroku
A aplicação está deployada em: [gerenciador-clientes-api-luan.herokuapp.com](https://gerenciador-clientes-api-luan-50f831b39a9a.herokuapp.com)

### Docker
```bash
docker-compose up -d             # Full stack (app + banco)
docker-compose up postgres      # Apenas banco
```