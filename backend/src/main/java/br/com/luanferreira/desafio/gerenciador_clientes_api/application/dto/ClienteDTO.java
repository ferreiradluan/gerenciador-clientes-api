package br.com.luanferreira.desafio.gerenciador_clientes_api.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {

    private Long id;
    private String nome;
    private String cpf; // CPF com máscara para exibição
    private String cpfLimpo; // CPF limpo para processamento interno
    private Set<TelefoneDTO> telefones;
    private Set<EmailDTO> emails;
    private List<EnderecoResponseDTO> enderecos;
}
