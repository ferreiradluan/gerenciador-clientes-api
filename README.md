# Gerenciador de Clientes

Sistema completo para gerenciamento de clientes com arquitetura separada entre backend e frontend.

## Estrutura do Projeto

```
gerenciador-clientes-api/
├── backend/                 # API RESTful Spring Boot
│   ├── src/
│   ├── pom.xml
│   ├── mvnw
│   └── ...
├── frontend/               # Aplicação Angular (a ser criada)
│   └── ...
└── README.md
```

## Backend (Spring Boot)

A API RESTful desenvolvida em Spring Boot com as seguintes tecnologias:

- **Java 17**
- **Spring Boot 3.5.3**
- **Spring Data JPA**
- **Spring Security**
- **Spring Validation**
- **PostgreSQL**
- **Lombok**

### Como executar o backend

```bash
cd backend
./mvnw spring-boot:run
```

### Funcionalidades do Backend

- API RESTful para gerenciamento de clientes
- Autenticação e autorização com Spring Security
- Validação de dados
- Persistência com PostgreSQL
- Documentação da API (a ser implementada)

## Frontend (Angular)

*A ser implementado*

O frontend será desenvolvido em Angular e consumirá a API do backend.

## Configuração do Ambiente

### Pré-requisitos

- Java 17+
- Maven 3.6+
- PostgreSQL
- Node.js (para o frontend Angular)

### Configuração do Banco de Dados

Configure as propriedades de conexão com o PostgreSQL no arquivo `backend/src/main/resources/application.properties`.

## Desenvolvimento

1. **Backend**: Inicie o servidor Spring Boot
2. **Frontend**: Inicie o servidor de desenvolvimento Angular (quando criado)

## Próximos Passos

- [ ] Configurar banco de dados PostgreSQL
- [ ] Implementar entidades e repositórios
- [ ] Criar controllers da API
- [ ] Implementar autenticação
- [ ] Criar projeto Angular
- [ ] Implementar interface de usuário
- [ ] Integrar frontend com backend
