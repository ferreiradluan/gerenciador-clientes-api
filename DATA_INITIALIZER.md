# Data Initializer - Gerenciador de Clientes API

## Visão Geral

O `DataInitializer` é um componente responsável por garantir que os usuários padrão do sistema existam no banco de dados durante a inicialização da aplicação.

## Funcionalidade

Este componente implementa `CommandLineRunner` e é executado automaticamente quando a aplicação Spring Boot é iniciada. Ele verifica se os usuários padrão existem no banco de dados e os cria caso não existam.

### Usuários Criados

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

## Implementação

O componente faz uso de:

- **UsuarioRepository**: Para interação com o banco de dados
- **PasswordEncoder**: Para criptografar as senhas usando BCrypt
- **Usuario**: Entidade JPA que representa um usuário no sistema

## Segurança

- As senhas são automaticamente criptografadas usando BCrypt antes de serem salvas no banco
- O componente verifica se os usuários já existem antes de tentar criá-los, evitando duplicação
- As credenciais ficam protegidas no banco de dados com hash BCrypt

## Estrutura do Banco

A tabela `usuarios` é criada automaticamente pelo Hibernate com a seguinte estrutura:

```sql
CREATE TABLE usuarios (
    id BIGSERIAL PRIMARY KEY,
    login VARCHAR(255) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL
);
```

## Uso

Para utilizar as credenciais criadas:

1. Faça uma requisição POST para `/api/auth/login` com:
   ```json
   {
       "username": "admin",
       "password": "123qwe!@#"
   }
   ```

2. Use o token JWT retornado nos headers das próximas requisições:
   ```
   Authorization: Bearer <token>
   ```

## Logs

Durante a inicialização, você pode verificar no console se os usuários foram criados ou se já existiam no banco de dados.
