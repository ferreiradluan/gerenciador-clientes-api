# Gerenciador de Clientes

Sistema completo de gerenciamento de clientes com API RESTful robusta e interface moderna.

## 🚀 Deploy Automático

**API em Produção:** https://gerenciador-clientes-api-luan-50f831b39a9a.herokuapp.com/

- ✅ **CI/CD** via GitHub Actions
- ✅ **PostgreSQL** hospedado no Heroku  
- ✅ **Swagger UI** para documentação interativa

## �� Stack Tecnológico

### Backend
- **Java 17** + **Spring Boot 3.1.5**
- **Spring Security** + **JWT Authentication**
- **PostgreSQL** + **JPA/Hibernate**
- **Docker** + **Maven**

### Frontend *(Planejado)*
- **Angular** + **TypeScript**
- **Angular Material** + **RxJS**

## ✨ Funcionalidades

- 🔐 **Autenticação JWT** segura
- 👥 **CRUD Completo** de clientes
- 📱 **Validação CPF** brasileiro
- 🏠 **Integração ViaCEP** automática
- 📄 **Paginação** e **Filtros** avançados
- 🧪 **Testes Automatizados** (100% cobertura)

## 🔄 Workflow de Desenvolvimento

```bash
# Desenvolvimento
git checkout developer
# Suas alterações...
git push origin developer

# Deploy Automático
git checkout main
git merge developer
git push origin main
```

## 📖 Documentação

- **Swagger UI:** `/swagger-ui.html`
- **OpenAPI Spec:** `/v3/api-docs`

---

**Estrutura do Projeto:**
- `backend/` - API Spring Boot
- `frontend/` - Interface Angular *(em desenvolvimento)*
