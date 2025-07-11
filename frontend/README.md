# Gerenciador de Clientes - Frontend

*Esta pasta está reservada para o projeto Angular que será criado posteriormente.*

## Tecnologias Planejadas

- **Angular** (versão mais recente)
- **TypeScript**
- **Angular Material** ou **Bootstrap** para UI
- **RxJS** para programação reativa
- **Angular HTTP Client** para comunicação com a API

## Estrutura Planejada

```
frontend/
├── src/
│   ├── app/
│   │   ├── components/
│   │   │   ├── cliente-list/
│   │   │   ├── cliente-form/
│   │   │   └── cliente-detail/
│   │   ├── services/
│   │   │   └── cliente.service.ts
│   │   ├── models/
│   │   │   └── cliente.model.ts
│   │   ├── guards/
│   │   ├── interceptors/
│   │   └── shared/
│   ├── assets/
│   ├── environments/
│   └── styles/
├── package.json
├── angular.json
└── tsconfig.json
```

## Como Criar o Projeto Angular

```bash
# Instalar Angular CLI globalmente (se não estiver instalado)
npm install -g @angular/cli

# Criar projeto Angular nesta pasta
ng new gerenciador-clientes-frontend --routing --style=scss

# Ou criar diretamente na pasta atual
ng new . --routing --style=scss --skip-git
```

## Funcionalidades Planejadas

- [ ] Listagem de clientes
- [ ] Cadastro de novos clientes
- [ ] Edição de clientes existentes
- [ ] Exclusão de clientes
- [ ] Busca e filtros
- [ ] Paginação
- [ ] Validação de formulários
- [ ] Tratamento de erros
- [ ] Loading states
- [ ] Design responsivo

## Integração com Backend

O frontend consumirá a API REST do backend Spring Boot:

- **Base URL**: `http://localhost:8080/api`
- **Comunicação**: HTTP Client com interceptors
- **Autenticação**: JWT tokens (quando implementado)
- **Tratamento de erros**: Interceptors para erros HTTP

## Desenvolvimento

### Comandos Úteis (após criar o projeto)

```bash
# Instalar dependências
npm install

# Executar em modo de desenvolvimento
ng serve

# Build para produção
ng build --prod

# Executar testes
ng test

# Executar e2e tests
ng e2e
```

### Bibliotecas Recomendadas

```bash
# Angular Material
ng add @angular/material

# Bootstrap
npm install bootstrap

# Reactive Forms
# (já incluído no Angular)

# HTTP Interceptors
# (já incluído no Angular)
```

## Próximos Passos

1. Criar projeto Angular
2. Configurar estrutura de pastas
3. Implementar serviço para comunicação com API
4. Criar modelos TypeScript
5. Implementar componentes de interface
6. Configurar roteamento
7. Implementar formulários reativos
8. Adicionar validações
9. Implementar tratamento de erros
10. Adicionar testes
