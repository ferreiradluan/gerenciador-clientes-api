# 🎯 Gerenciador de Clientes

> Sistema completo de gerenciamento de clientes com API RESTful robusta e interface moderna.

[![Java](https://img.shields.io/badge/Java-17-orange)](https://openjdk.java.net/projects/jdk/17/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.5-brightgreen)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue)](https://www.postgresql.org/)
[![Deploy](https://img.shields.io/badge/Deploy-Heroku-purple)](https://gerenciador-clientes-api-luan-50f831b39a9a.herokuapp.com)

## 🌐 Aplicação em Produção

**API Backend:** https://gerenciador-clientes-api-luan-50f831b39a9a.herokuapp.com/

- ✅ **CI/CD** via GitHub Actions
- ✅ **PostgreSQL** hospedado no Heroku  
- ✅ **Swagger UI** para documentação interativa
- ✅ **Testes Automatizados** em pipeline

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
