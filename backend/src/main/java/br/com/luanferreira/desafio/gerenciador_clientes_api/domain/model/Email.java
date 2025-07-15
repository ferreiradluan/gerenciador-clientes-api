package br.com.luanferreira.desafio.gerenciador_clientes_api.domain.model;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Email {

    @NotBlank(message = "O endereço de e-mail não pode ser nulo ou vazio")
    @javax.validation.constraints.Email(message = "Formato de e-mail inválido")
    private String enderecoEmail;
}
