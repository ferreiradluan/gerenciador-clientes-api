package br.com.luanferreira.desafio.gerenciador_clientes_api.domain.model;

import br.com.luanferreira.desafio.gerenciador_clientes_api.application.util.TelefoneUtil;
import br.com.luanferreira.desafio.gerenciador_clientes_api.application.validation.ValidTelefone;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@ValidTelefone
public class Telefone {

    @NotBlank(message = "O DDD não pode ser nulo ou vazio")
    @Size(min = 2, max = 2, message = "O DDD deve ter 2 dígitos")
    @Pattern(regexp = "\\d{2}", message = "O DDD deve conter apenas números")
    private String ddd;

    @NotBlank(message = "O número do telefone não pode ser nulo ou vazio")
    private String numero;

    @NotBlank(message = "O tipo do telefone não pode ser nulo ou vazio")
    @Pattern(regexp = "^(CELULAR|RESIDENCIAL|COMERCIAL)$", message = "Tipo deve ser CELULAR, RESIDENCIAL ou COMERCIAL")
    private String tipo;

    /**
     * Valida se o número está correto baseado no tipo
     */
    public boolean isNumeroValido() {
        return TelefoneUtil.isValido(this.ddd, this.numero, this.tipo);
    }

    /**
     * Retorna o telefone formatado
     */
    public String getFormatado() {
        return TelefoneUtil.aplicarMascara(this.ddd, this.numero, this.tipo);
    }
}
