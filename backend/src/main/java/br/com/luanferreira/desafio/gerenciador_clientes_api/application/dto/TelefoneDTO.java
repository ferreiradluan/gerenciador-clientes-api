package br.com.luanferreira.desafio.gerenciador_clientes_api.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TelefoneDTO {
    private String ddd;
    private String numero;
    private String tipo;
    private String formatado; // Campo adicional para telefone formatado
}
