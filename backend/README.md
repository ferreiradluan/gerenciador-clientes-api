# API de Gerenciamento de Clientes

## Exemplos de Uso da API

### Criar um novo cliente

`POST /api/clientes`

**Request Body:**

```json
{
  "nome": "Fulano de Tal",
  "cpf": "12345678901",
  "telefones": [
    {
      "ddd": "11",
      "numero": "999999999",
      "tipo": "Celular"
    }
  ],
  "emails": [
    {
      "enderecoEmail": "fulano@example.com"
    }
  ],
  "enderecos": [
    {
      "cep": "01001000",
      "complemento": "Apto 101"
    }
  ]
}
```

### Listar clientes com paginação e filtro

`GET /api/clientes?page=0&size=10&nome=Fulano`

### Buscar cliente por ID

`GET /api/clientes/1`

### Atualizar cliente

`PUT /api/clientes/1`

**Request Body:**

```json
{
  "nome": "Fulano de Tal Atualizado",
  "cpf": "12345678901",
  "telefones": [
    {
      "ddd": "11",
      "numero": "988888888",
      "tipo": "Celular"
    }
  ],
  "emails": [
    {
      "enderecoEmail": "fulano.atualizado@example.com"
    }
  ],
  "enderecos": [
    {
      "cep": "01001000",
      "complemento": "Apto 102"
    }
  ]
}
```

### Deletar cliente

`DELETE /api/clientes/1`