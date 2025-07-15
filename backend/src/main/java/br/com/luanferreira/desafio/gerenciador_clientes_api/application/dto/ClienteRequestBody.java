package br.com.luanferreira.desafio.gerenciador_clientes_api.application.dto;

import br.com.luanferreira.desafio.gerenciador_clientes_api.domain.model.Email;
import br.com.luanferreira.desafio.gerenciador_clientes_api.domain.model.Telefone;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ0-9\\s]+$", message = "O nome deve conter apenas letras, números e espaços")
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
