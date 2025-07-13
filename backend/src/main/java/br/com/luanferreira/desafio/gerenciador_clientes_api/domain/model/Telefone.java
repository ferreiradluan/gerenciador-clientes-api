package br.com.luanferreira.desafio.gerenciador_clientes_api.domain.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Telefone {

    @NotBlank(message = "O DDD não pode ser nulo ou vazio")
    @Size(min = 2, max = 2, message = "O DDD deve ter 2 dígitos")
    private String ddd;

    @NotBlank(message = "O número do telefone não pode ser nulo ou vazio")
    @Size(min = 8, max = 9, message = "O número do telefone deve ter entre 8 e 9 dígitos")
    private String numero;

    @NotBlank(message = "O tipo do telefone não pode ser nulo ou vazio")
    private String tipo;
}
