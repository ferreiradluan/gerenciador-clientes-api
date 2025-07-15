package br.com.luanferreira.desafio.gerenciador_clientes_api.util;

import br.com.luanferreira.desafio.gerenciador_clientes_api.application.dto.ClienteRequestBody;
import br.com.luanferreira.desafio.gerenciador_clientes_api.application.dto.EnderecoDTO;
import br.com.luanferreira.desafio.gerenciador_clientes_api.domain.model.Cliente;
import br.com.luanferreira.desafio.gerenciador_clientes_api.domain.model.Email;
import br.com.luanferreira.desafio.gerenciador_clientes_api.domain.model.Endereco;
import br.com.luanferreira.desafio.gerenciador_clientes_api.domain.model.Telefone;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Classe utilitária para criação de objetos de teste
 * Centraliza a criação de dados de teste para manter consistência
 */
public class TestDataBuilder {

    public static final String CPF_VALIDO = "11144477735";
    public static final String CEP_VALIDO = "01001000";
    
    public static ClienteRequestBody criarClienteRequestValido() {
        return new ClienteRequestBody(
                "João Silva",
                CPF_VALIDO,
                new HashSet<>(Collections.singletonList(criarTelefoneValido())),
                new HashSet<>(Collections.singletonList(criarEmailValido())),
                Collections.singletonList(criarEnderecoValido())
        );
    }

    public static ClienteRequestBody criarClienteRequestComNome(String nome) {
        ClienteRequestBody request = criarClienteRequestValido();
        request.setNome(nome);
        return request;
    }

    public static ClienteRequestBody criarClienteRequestComCpf(String cpf) {
        ClienteRequestBody request = criarClienteRequestValido();
        request.setCpf(cpf);
        return request;
    }

    public static Cliente criarClienteComId(Long id) {
        Cliente cliente = new Cliente();
        cliente.setId(id);
        cliente.setNome("João Silva");
        cliente.setCpf(CPF_VALIDO);
        cliente.setTelefones(new HashSet<>(Collections.singletonList(criarTelefoneValido())));
        cliente.setEmails(new HashSet<>(Collections.singletonList(criarEmailValido())));
        cliente.setEnderecos(Collections.singletonList(criarEnderecoEntity()));
        return cliente;
    }

    public static Cliente criarClienteSemId() {
        return criarClienteComId(null);
    }

    public static Telefone criarTelefoneValido() {
        return new Telefone("11", "987654321", "CELULAR");
    }

    public static Email criarEmailValido() {
        return new Email("joao@teste.com");
    }

    public static EnderecoDTO criarEnderecoValido() {
        return new EnderecoDTO(CEP_VALIDO, null, null, null, null, "Apto 101");
    }

    public static EnderecoDTO criarEnderecoViaCep() {
        return new EnderecoDTO(CEP_VALIDO, "Praça da Sé", "Sé", "São Paulo", "SP", null);
    }

    public static Endereco criarEnderecoEntity() {
        Endereco endereco = new Endereco();
        endereco.setId(1L);
        endereco.setCep(CEP_VALIDO);
        endereco.setLogradouro("Praça da Sé");
        endereco.setBairro("Sé");
        endereco.setCidade("São Paulo");
        endereco.setUf("SP");
        endereco.setComplemento("Apto 101");
        return endereco;
    }

    /**
     * Cria dados para teste de validação com dados inválidos
     */
    public static class DadosInvalidos {
        
        public static ClienteRequestBody comCpfInvalido() {
            ClienteRequestBody request = criarClienteRequestValido();
            request.setCpf("12345678900"); // CPF inválido
            return request;
        }

        public static ClienteRequestBody comNomeVazio() {
            ClienteRequestBody request = criarClienteRequestValido();
            request.setNome("");
            return request;
        }

        public static ClienteRequestBody comEmailInvalido() {
            ClienteRequestBody request = criarClienteRequestValido();
            request.setEmails(new HashSet<>(Collections.singletonList(new Email("email-invalido"))));
            return request;
        }

        public static ClienteRequestBody comTelefoneInvalido() {
            ClienteRequestBody request = criarClienteRequestValido();
            request.setTelefones(new HashSet<>(Collections.singletonList(new Telefone("1", "123", "CELULAR")))); // DDD e número inválidos
            return request;
        }
    }
}
