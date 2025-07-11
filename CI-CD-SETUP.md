# Backend CI/CD Configuration Guide

## Configuração dos Secrets no GitHub

Para que o CI/CD funcione corretamente, você precisa configurar os seguintes secrets no seu repositório GitHub:

### 1. Acesse as configurações do repositório
- Vá para `Settings` > `Secrets and variables` > `Actions`

### 2. Configure os secrets para o Heroku (Backend):

```
HEROKU_API_KEY: Sua chave API do Heroku
HEROKU_APP_NAME: Nome da sua aplicação no Heroku
HEROKU_EMAIL: Seu email do Heroku
```

## Configuração do Heroku

### 1. Criando a aplicação
```bash
heroku create nome-da-sua-app
```

### 2. Adicionando PostgreSQL
```bash
heroku addons:create heroku-postgresql:mini -a nome-da-sua-app
```

### 3. Configurando variáveis de ambiente no Heroku
```bash
# CORS para o frontend (quando estiver pronto)
heroku config:set CORS_ALLOWED_ORIGINS=https://seu-frontend.vercel.app -a nome-da-sua-app

# Profile de produção
heroku config:set SPRING_PROFILES_ACTIVE=prod -a nome-da-sua-app
```

### 4. Obtendo a API Key do Heroku
```bash
heroku auth:token
```

### Branch `developer`
- **Propósito**: Desenvolvimento e testes locais
- **Ações do CI**:
  - Executa testes automatizados do backend
  - Valida build da aplicação Spring Boot
  - Análise de código Java

### Branch `main`
- **Propósito**: Produção
- **Ações do CI/CD**:
  - Executa todos os testes do backend
  - Build da aplicação Spring Boot
  - Deploy automático para Heroku (backend)

## Ambientes de banco de dados

### Desenvolvimento local (`developer`)
- PostgreSQL rodando via Docker
- Configurado no `docker-compose.yml`
- Profile: `default` ou `docker`

### Produção (`main`)
- PostgreSQL do Heroku (addon)
- Configurado automaticamente via `DATABASE_URL`
- Profile: `prod`

## Como usar

1. **Desenvolva na branch `developer`**:
   ```bash
   git checkout developer
   # Faça suas alterações
   git add .
   git commit -m "feat: nova funcionalidade"
   git push origin developer
   ```

2. **Para deploy em produção**:
   ```bash
   git checkout main
   git merge developer
   git push origin main
   ```

3. **Para testar localmente**:
   ```bash
   cd backend
   ./dev.sh start  # ou docker-compose up -d postgres
   ./mvnw spring-boot:run
   ```

## Monitoramento

- **GitHub Actions**: Veja os logs em `Actions` no GitHub
- **Heroku**: `heroku logs --tail -a nome-da-sua-app`

## Troubleshooting

### Build falhou no Heroku
- Verifique se o `system.properties` está configurado corretamente
- Confirme que todas as variáveis de ambiente estão definidas
- Verifique os logs: `heroku logs --tail -a nome-da-sua-app`

### Problemas de CORS
- Atualize a variável `CORS_ALLOWED_ORIGINS` no Heroku
- Verifique se a URL do frontend está correta

### Problemas de conexão com banco
- Verifique se o addon PostgreSQL está ativo: `heroku addons -a nome-da-sua-app`
- Confirme que a `DATABASE_URL` está sendo injetada automaticamente
