package br.com.luanferreira.desafio.gerenciador_clientes_api.application.mapper;

import br.com.luanferreira.desafio.gerenciador_clientes_api.application.dto.*;
import br.com.luanferreira.desafio.gerenciador_clientes_api.domain.model.Cliente;
import br.com.luanferreira.desafio.gerenciador_clientes_api.domain.model.Email;
import br.com.luanferreira.desafio.gerenciador_clientes_api.domain.model.Endereco;
import br.com.luanferreira.desafio.gerenciador_clientes_api.domain.model.Telefone;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Interface do MapStruct para mapear entre a entidade Cliente e seus DTOs
 */
@Mapper(componentModel = "spring")
public interface ClienteMapper {

    /**
     * Converte ClienteRequestBody para entidade Cliente
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enderecos", ignore = true) // Endereços são tratados separadamente no service
    @Mapping(target = "cpf", expression = "java(br.com.luanferreira.desafio.gerenciador_clientes_api.application.util.CpfUtil.removerMascara(dto.getCpf()))")
    Cliente toEntity(ClienteRequestBody dto);

    /**
     * Converte entidade Cliente para ClienteDTO com máscaras aplicadas
     */
    default ClienteDTO toDTO(Cliente cliente) {
        if (cliente == null) {
            return null;
        }
        
        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId());
        dto.setNome(cliente.getNome());
        dto.setCpf(br.com.luanferreira.desafio.gerenciador_clientes_api.application.util.CpfUtil.aplicarMascara(cliente.getCpf()));
        dto.setCpfLimpo(cliente.getCpf());
        
        // Converte telefones
        if (cliente.getTelefones() != null) {
            dto.setTelefones(cliente.getTelefones().stream()
                    .map(this::telefoneToDTO)
                    .collect(Collectors.toSet()));
        }
        
        // Converte emails
        if (cliente.getEmails() != null) {
            dto.setEmails(cliente.getEmails().stream()
                    .map(this::emailToDTO)
                    .collect(Collectors.toSet()));
        }
        
        // Converte endereços
        if (cliente.getEnderecos() != null) {
            dto.setEnderecos(cliente.getEnderecos().stream()
                    .map(this::enderecoToDTO)
                    .collect(Collectors.toList()));
        }
        
        return dto;
    }

    /**
     * Converte lista de Clientes para lista de ClienteDTOs
     */
    default List<ClienteDTO> toDTO(List<Cliente> clientes) {
        return clientes.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Atualiza uma entidade Cliente existente com dados do DTO
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enderecos", ignore = true) // Endereços são tratados separadamente no service
    @Mapping(target = "cpf", expression = "java(br.com.luanferreira.desafio.gerenciador_clientes_api.application.util.CpfUtil.removerMascara(dto.getCpf()))")
    void updateClienteFromDto(ClienteRequestBody dto, @MappingTarget Cliente cliente);

    /**
     * Converte Page de Cliente para Page de ClienteDTO
     */
    default org.springframework.data.domain.Page<ClienteDTO> toDTO(Page<Cliente> clientePage) {
        return clientePage.map(this::toDTO);
    }
    
    // Métodos auxiliares para conversão
    default TelefoneDTO telefoneToDTO(Telefone telefone) {
        if (telefone == null) {
            return null;
        }
        return new TelefoneDTO(
                telefone.getDdd(),
                telefone.getNumero(),
                telefone.getTipo(),
                telefone.getFormatado()
        );
    }
    
    default EmailDTO emailToDTO(Email email) {
        if (email == null) {
            return null;
        }
        return new EmailDTO(email.getEnderecoEmail());
    }
    
    default EnderecoResponseDTO enderecoToDTO(Endereco endereco) {
        if (endereco == null) {
            return null;
        }
        return new EnderecoResponseDTO(
                endereco.getId(),
                br.com.luanferreira.desafio.gerenciador_clientes_api.application.util.CepUtil.aplicarMascara(endereco.getCep()),
                endereco.getCep(),
                endereco.getLogradouro(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getUf(),
                endereco.getComplemento()
        );
    }
}
