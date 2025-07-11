# Gerenciador de Clientes

API RESTful para gerenciamento de clientes desenvolvida em Spring Boot com PostgreSQL e deploy automatizado no Heroku.

## 🚀 Deploy e CI/CD

- **URL da API**: https://gerenciador-clientes-api-luan-50f831b39a9a.herokuapp.com/
- **CI/CD**: GitHub Actions
- **Deploy**: Heroku (automático na branch main)

## 📊 Status dos Ambientes

### Development (Branch: developer)
- ✅ Testes automatizados
- ✅ Build da aplicação  
- 🔄 PostgreSQL local via Docker

### Production (Branch: main)
- ✅ Deploy automático no Heroku
- ✅ PostgreSQL do Heroku
- ✅ Configuração de produção

## 🛠 Tecnologias

### Backend
- **Java 17**
- **Spring Boot 3.5.3**
- **Spring Data JPA**
- **Spring Security**
- **Spring Validation**
- **PostgreSQL**
- **Lombok**
- **Maven**

### DevOps
- **Docker & Docker Compose**
- **GitHub Actions**
- **Heroku**

## 📁 Estrutura do Projeto

```
gerenciador-clientes-api/
├── backend/                    # API RESTful Spring Boot
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/          # Código fonte
│   │   │   └── resources/     # Configurações
│   │   └── test/              # Testes
│   ├── Dockerfile
│   ├── Procfile               # Heroku
│   ├── system.properties      # Heroku Java version
│   └── pom.xml
├── .github/workflows/         # CI/CD GitHub Actions
├── frontend/                  # Frontend Angular (futuro)
└── dev.sh                    # Scripts de desenvolvimento
```

## 🚀 Como Executar

### Desenvolvimento Local

```bash
# Opção 1: Docker (Recomendado)
./dev.sh start

# Opção 2: Apenas banco via Docker + API local
./dev.sh db
cd backend
./mvnw spring-boot:run

# Opção 3: Tudo local (requer PostgreSQL instalado)
cd backend
./mvnw spring-boot:run
```

### Ambientes Disponíveis

- **Local**: http://localhost:8080
- **Produção**: https://gerenciador-clientes-api-luan-50f831b39a9a.herokuapp.com/

## 🔄 Fluxo de Trabalho

### 1. Desenvolvimento
```bash
git checkout developer
# Faça suas alterações
git add .
git commit -m "feat: nova funcionalidade"
git push origin developer
```

### 2. Deploy para Produção
```bash
git checkout main
git merge developer
git push origin main  # Deploy automático no Heroku
```

## 📋 Monitoramento

```bash
# Logs do Heroku
heroku logs --tail -a gerenciador-clientes-api-luan

# Status da aplicação
heroku ps -a gerenciador-clientes-api-luan

# Conectar ao banco
heroku pg:psql -a gerenciador-clientes-api-luan
```

## 🎯 Próximos Passos

- [ ] Implementar entidades de Cliente
- [ ] Criar endpoints da API
- [ ] Implementar autenticação JWT
- [ ] Adicionar documentação Swagger
- [ ] Implementar testes unitários e integração
- [ ] Criar frontend Angular
