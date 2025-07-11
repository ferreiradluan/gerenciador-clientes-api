# CI/CD Configuration Guide - Backend Spring Boot

## Repositório
- **GitHub**: git@github.com:ferreiradluan/gerenciador-clientes-api.git
- **Tecnologia**: Spring Boot + PostgreSQL
- **Deploy**: Heroku

## Configuração dos Secrets no GitHub

### 1. Acesse as configurações do repositório
- Vá para `Settings` > `Secrets and variables` > `Actions`

### 2. Configure os secrets para o Heroku:

```
HEROKU_API_KEY: Sua chave API do Heroku
HEROKU_APP_NAME: gerenciador-clientes-api-luan
HEROKU_EMAIL: Seu email do Heroku
```

## Configuração do Heroku

### 1. Instalação do Heroku CLI
```bash
# Ubuntu/Debian
curl https://cli-assets.heroku.com/install.sh | sh

# Ou via snap
sudo snap install heroku --classic
```

### 2. Login no Heroku
```bash
heroku login
```

### 3. Criando a aplicação
```bash
heroku create gerenciador-clientes-api-luan
```

### 4. Adicionando PostgreSQL
```bash
heroku addons:create heroku-postgresql:mini -a gerenciador-clientes-api-luan
```

### 5. Configurando variáveis de ambiente
```bash
# CORS para permitir acesso
heroku config:set CORS_ALLOWED_ORIGINS=* -a gerenciador-clientes-api-luan

# Profile de produção
heroku config:set SPRING_PROFILES_ACTIVE=prod -a gerenciador-clientes-api-luan
```

### 6. Conectando o Git
```bash
heroku git:remote -a gerenciador-clientes-api-luan
```

### 7. Obtendo a API Key
```bash
heroku auth:token
```

## Fluxo de Trabalho (Git Flow)

### Branch `developer` (Desenvolvimento):
- ✅ Testes automatizados
- ✅ Build da aplicação
- ✅ Validação de código
- 🔄 **Banco PostgreSQL local via Docker**

### Branch `main` (Produção):
- ✅ Testes automatizados
- ✅ Build da aplicação
- ✅ **Deploy automático para Heroku**
- 🔄 **Banco PostgreSQL do Heroku**

## Ambientes de Banco de Dados

### Desenvolvimento local (`developer`)
```properties
# application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/gerenciador_clientes_db
spring.datasource.username=postgres
spring.datasource.password=postgres
```

### Produção (`main`)
```properties
# application-prod.properties
spring.datasource.url=${DATABASE_URL}  # Automaticamente configurado pelo Heroku
```

## Como usar

### 1. Desenvolvimento na branch `developer`:
```bash
git checkout developer
# Faça suas alterações
git add .
git commit -m "feat: implementação de funcionalidade"
git push origin developer
```

### 2. Deploy para produção:
```bash
git checkout main
git merge developer
git push origin main  # Isso vai disparar o deploy automático
```

### 3. Testar localmente:
```bash
cd backend
./mvnw spring-boot:run
```

### 4. Testar com Docker:
```bash
./dev.sh start  # Inicia PostgreSQL + API
./dev.sh db     # Só o PostgreSQL
./dev.sh stop   # Para tudo
```

## Monitoramento e Logs

### GitHub Actions
- Acesse `Actions` no repositório GitHub
- Veja os logs de build e deploy

### Heroku
```bash
# Ver logs da aplicação
heroku logs --tail -a gerenciador-clientes-api-luan

# Ver status da aplicação
heroku ps -a gerenciador-clientes-api-luan

# Abrir aplicação no browser
heroku open -a gerenciador-clientes-api-luan
```

## Endpoints da API

### Desenvolvimento
- Base URL: `http://localhost:8080`

### Produção
- Base URL: `https://gerenciador-clientes-api-luan.herokuapp.com`

## Troubleshooting

### Build falhou no Heroku
```bash
# Verificar logs
heroku logs --tail -a gerenciador-clientes-api-luan

# Verificar configurações
heroku config -a gerenciador-clientes-api-luan

# Verificar addons
heroku addons -a gerenciador-clientes-api-luan
```

### Problema de conexão com banco
```bash
# Verificar DATABASE_URL
heroku config:get DATABASE_URL -a gerenciador-clientes-api-luan

# Conectar ao banco via psql
heroku pg:psql -a gerenciador-clientes-api-luan
```

### Reiniciar aplicação
```bash
heroku restart -a gerenciador-clientes-api-luan
```
