package br.com.luanferreira.desafio.gerenciador_clientes_api.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoResponseDTO {
    private Long id;
    private String cep; // CEP com máscara
    private String cepLimpo; // CEP sem máscara (para uso interno)
    private String logradouro;
    private String bairro;
    private String cidade;
    private String uf;
    private String complemento;
}
