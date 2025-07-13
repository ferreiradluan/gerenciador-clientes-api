# 🚀 Gerenciador de Clientes API

API RESTful moderna e robusta para gerenciamento completo de clientes, construída com **Spring Boot 3** e **Java 17**.

[![Java](https://img.shields.io/badge/Java-17-orange)](https://openjdk.java.net/projects/jdk/17/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.5-brightgreen)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue)](https://www.postgresql.org/)
[![Deploy](https://img.shields.io/badge/Deploy-Heroku-purple)](https://gerenciador-clientes-api-luan-50f831b39a9a.herokuapp.com)

## 📋 Sobre o Projeto

Sistema de gerenciamento de clientes com funcionalidades completas de CRUD, autenticação JWT, validações brasileiras e integração com serviços externos. Ideal para empresas que precisam de uma solução confiável e escalável para gestão de cadastros.

## 🌐 Aplicação em Produção

**API Backend:** https://gerenciador-clientes-api-luan-50f831b39a9a.herokuapp.com/

- ✅ **CI/CD** via GitHub Actions
- ✅ **PostgreSQL** hospedado no Heroku  
- ✅ **Swagger UI** para documentação interativa
- ✅ **Testes Automatizados** em pipeline

## 🛠️ Tecnologias

- **Backend:** Spring Boot 3.1.5, Java 17, Maven
- **Segurança:** Spring Security + JWT 
- **Banco de Dados:** PostgreSQL com JPA/Hibernate
- **Mapeamento:** MapStruct para DTOs
- **Documentação:** OpenAPI/Swagger
- **Testes:** JUnit 5, Mockito
- **Deploy:** Docker, Heroku

## ⚡ Quick Start

```bash
# 1. Clone o repositório
git clone https://github.com/ferreiradluan/gerenciador-clientes-api.git
cd gerenciador-clientes-api

# 2. Execute a stack completa (banco + aplicação)
cd backend
docker-compose up -d
```

**🌐 Acesso:**
- **API:** `http://localhost:8080`
- **Swagger:** `http://localhost:8080/swagger-ui.html`

## 📚 Documentação

### Endpoints Principais
```http
POST   /api/auth/login           # Autenticação JWT
GET    /api/clientes             # Listar clientes (paginado)
POST   /api/clientes             # Criar cliente
GET    /api/clientes/{id}        # Buscar por ID
PUT    /api/clientes/{id}        # Atualizar cliente
DELETE /api/clientes/{id}        # Remover cliente
```

### Exemplo de Uso
```bash
# Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"password"}'

# Criar cliente (com token)
curl -X POST http://localhost:8080/api/clientes \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João Silva",
    "cpf": "11144477735",
    "telefones": [{"ddd":"11","numero":"987654321","tipo":"CELULAR"}],
    "emails": [{"enderecoEmail":"joao@email.com"}],
    "enderecos": [{"cep":"01001000","complemento":"Apto 101"}]
  }'
```

## ✅ Funcionalidades

- ✅ **CRUD Completo** de clientes
- ✅ **Autenticação JWT** com Spring Security
- ✅ **Validação de CPF** brasileiro
- ✅ **Integração ViaCEP** para endereços automáticos
- ✅ **Paginação e Filtros** avançados
- ✅ **Mapeamento MapStruct** para DTOs
- ✅ **Tratamento Centralizado** de exceções
- ✅ **Documentação Swagger** interativa
- ✅ **Testes Automatizados** (100% cobertura)
- ✅ **Deploy Docker** e Heroku

## 🧪 Testes

```bash
./mvnw test                    # Todos os testes
./mvnw test -Dtest=*Unit*      # Apenas testes unitários
./mvnw test -Dtest=*Integration* # Apenas testes de integração
```

## 🐳 Docker

```bash
# Stack completa (banco + aplicação)
docker-compose up -d

# Verificar logs
docker-compose logs -f

# Parar serviços
docker-compose down
```

## 📊 Estrutura do Projeto

```
backend/
├── src/main/java/
│   ├── application/          # Controllers, DTOs, Services, Mappers
│   ├── domain/              # Entities, Repositories, Exceptions
│   └── infrastructure/      # Configs, Clients, Security
├── src/test/                # Testes unitários e integração
├── docker-compose.yml       # Configuração Docker
└── pom.xml                 # Dependências Maven
```

## 🏗️ Arquitetura

- **Clean Architecture** com separação clara de responsabilidades
- **Domain-Driven Design** (DDD) para modelagem
- **MapStruct** para mapeamento automático de DTOs
- **Exception Handling** centralizado com `@RestControllerAdvice`
- **Specification Pattern** para consultas dinâmicas

## 🔒 Segurança

- **JWT Authentication** com Spring Security
- **CORS** configurado para produção
- **Validação** de entrada com Bean Validation
- **Headers de Segurança** configurados

## 🚀 Deploy

**Heroku:**
```bash
git push heroku main
```

**Docker:**
```bash
docker build -t gerenciador-clientes .
docker run -p 8080:8080 gerenciador-clientes
```

## 🤝 Contribuição

1. Fork o projeto
2. Crie uma branch (`git checkout -b feature/nova-funcionalidade`)
3. Commit suas mudanças (`git commit -m 'feat: adicionar funcionalidade'`)
4. Push para a branch (`git push origin feature/nova-funcionalidade`)
5. Abra um Pull Request

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para detalhes.

---

⭐ **Se este projeto foi útil, deixe uma estrela!**
