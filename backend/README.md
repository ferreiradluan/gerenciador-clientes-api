# 🎯 Gerenciador de Clientes API

API RESTful para gerenciamento de clientes com autenticação JWT e validações brasileiras.

[![Java](https://img.shields.io/badge/Java-8-orange)](https://openjdk.java.net/projects/jdk8/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7.18-brightgreen)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue)](https://www.postgresql.org/)

## 🌐 Produção

**API:** https://gerenciador-clientes-api-luan-50f831b39a9a.herokuapp.com/swagger-ui/index.html

## � Credenciais de Acesso

1. **Admin** - Login: `admin` | Senha: `123qwe!@#` (CRUD completo)
2. **Usuário** - Login: `user` | Senha: `123qwe123` (Apenas leitura)

## 🚀 Executar com Docker

```bash
# Stack completa (API + PostgreSQL)
docker-compose up -d

# Acessar
# API: http://localhost:8080
# Swagger: http://localhost:8080/swagger-ui/index.html
```

## 🛠️ Tecnologias

- **Java 8** + **Spring Boot 2.7.18**
- **Spring Security** + **JWT**
- **PostgreSQL** + **JPA/Hibernate**
- **Maven** + **Docker**
- **OpenAPI/Swagger**

## � Endpoints Principais

```http
POST   /api/auth/login           # Autenticação
GET    /api/clientes             # Listar clientes
POST   /api/clientes             # Criar cliente
GET    /api/clientes/{id}        # Buscar por ID
PUT    /api/clientes/{id}        # Atualizar cliente
DELETE /api/clientes/{id}        # Remover cliente
```

## ✅ Funcionalidades

- ✅ **CRUD** completo de clientes
- ✅ **Autenticação JWT** 
- ✅ **Validação CPF** brasileiro
- ✅ **Integração ViaCEP**
- ✅ **Paginação e filtros**
- ✅ **Testes automatizados**

## 🧪 Testes

```bash
./mvnw test
```