# 🚀 Gerenciador de Clientes

Sistema completo para gerenciamento de clientes com API RESTful e interface web moderna.

[![Java](https://img.shields.io/badge/Java-17-orange)](https://openjdk.java.net/projects/jdk/17/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.5-brightgreen)](https://spring.io/projects/spring-boot)
[![Angular](https://img.shields.io/badge/Angular-20-red)](https://angular.io/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue)](https://www.postgresql.org/)

## 🌐 Aplicação em Produção

- **🔗 Frontend:** https://gerenciador-clientes-fw4yusbzh-luan-ferreiras-projects.vercel.app/login
- **🔗 API Backend:** https://gerenciador-clientes-api-luan-50f831b39a9a.herokuapp.com/swagger-ui/index.html

## 👤 Credenciais de Acesso

1. **Usuário Admin**
   - Login: `admin`
   - Senha: `123qwe!@#`
   - Role: `ADMIN`
   - Permissões: Acesso completo (CRUD) a todos os endpoints

2. **Usuário Padrão**
   - Login: `user`
   - Senha: `123qwe123`
   - Role: `USER`
   - Permissões: Apenas leitura (GET) nos endpoints de clientes

## 🛠️ Tecnologias

- **Backend:** Spring Boot 3.1.5 + Java 17
- **Frontend:** Angular 20 + TypeScript
- **Banco:** PostgreSQL
- **Segurança:** JWT + Spring Security
- **Deploy:** Docker + Heroku + Vercel

## ⚡ Executar Localmente

```bash
# 1. Clonar repositório
git clone https://github.com/ferreiradluan/gerenciador-clientes-api.git
cd gerenciador-clientes-api

# 2. Executar stack completa com Docker
docker-compose up -d

# 3. Acessar aplicações
# Frontend: http://localhost:4200
# Backend API: http://localhost:8080
# Swagger: http://localhost:8080/swagger-ui/index.html
```

## ✅ Funcionalidades

- ✅ **CRUD Completo** de clientes
- ✅ **Autenticação JWT** 
- ✅ **Validação CPF** brasileiro
- ✅ **Integração ViaCEP** para endereços
- ✅ **Interface responsiva** com Angular Material
- ✅ **Documentação Swagger** interativa
- ✅ **Testes automatizados**

## 🏗️ Estrutura do Projeto

```
├── backend/           # API Spring Boot
├── frontend/          # Interface Angular  
├── docker-compose.yml # Stack completa
└── README.md         # Documentação
```

## 📚 Documentação Detalhada

- [Backend README](./backend/README.md) - Instruções da API
- [Frontend README](./frontend/README.md) - Instruções da interface

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
