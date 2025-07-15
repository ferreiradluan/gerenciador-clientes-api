package br.com.luanferreira.desafio.gerenciador_clientes_api.application.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoDTO {

    @NotBlank(message = "O CEP não pode ser nulo ou vazio")
    @Pattern(regexp = "^\\d{8}$", message = "CEP deve conter exatamente 8 dígitos")
    private String cep;

    @Size(max = 200, message = "O logradouro deve ter no máximo 200 caracteres")
    private String logradouro;

    @Size(max = 100, message = "O bairro deve ter no máximo 100 caracteres")
    private String bairro;

    @Size(max = 100, message = "A cidade deve ter no máximo 100 caracteres")
    private String cidade;

    @Pattern(regexp = "^[A-Z]{2}$", message = "UF deve conter exatamente 2 letras maiúsculas")
    private String uf;

    @Size(max = 200, message = "O complemento deve ter no máximo 200 caracteres")
    private String complemento;
}
