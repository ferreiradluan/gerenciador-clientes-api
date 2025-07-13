package br.com.luanferreira.desafio.gerenciador_clientes_api.application.dto;

import br.com.luanferreira.desafio.gerenciador_clientes_api.domain.model.Email;
import br.com.luanferreira.desafio.gerenciador_clientes_api.domain.model.Telefone;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteRequestBody {

    @NotBlank(message = "O nome não pode ser nulo ou vazio")
    private String nome;

    @NotBlank(message = "O CPF não pode ser nulo ou vazio")
    @CPF(message = "CPF inválido")
    private String cpf;

    @NotEmpty(message = "A lista de telefones não pode ser vazia")
    @Valid
    private Set<Telefone> telefones;

    @NotEmpty(message = "A lista de e-mails não pode ser vazia")
    @Valid
    private Set<Email> emails;

    @NotEmpty(message = "A lista de endereços não pode ser vazia")
    @Valid
    private List<EnderecoDTO> enderecos;
}
