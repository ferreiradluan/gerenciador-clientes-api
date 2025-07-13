package br.com.luanferreira.desafio.gerenciador_clientes_api.application.dto;

import br.com.luanferreira.desafio.gerenciador_clientes_api.domain.model.Email;
import br.com.luanferreira.desafio.gerenciador_clientes_api.domain.model.Telefone;
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
    private String cpf;
    private Set<Telefone> telefones;
    private Set<Email> emails;
    private List<EnderecoDTO> enderecos;
}
