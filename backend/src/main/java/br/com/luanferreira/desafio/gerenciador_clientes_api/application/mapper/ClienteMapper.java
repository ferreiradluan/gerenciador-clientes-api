package br.com.luanferreira.desafio.gerenciador_clientes_api.application.mapper;

import br.com.luanferreira.desafio.gerenciador_clientes_api.application.dto.ClienteDTO;
import br.com.luanferreira.desafio.gerenciador_clientes_api.application.dto.ClienteRequestBody;
import br.com.luanferreira.desafio.gerenciador_clientes_api.domain.model.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;

import java.util.List;

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
    Cliente toEntity(ClienteRequestBody dto);

    /**
     * Converte entidade Cliente para ClienteDTO
     */
    ClienteDTO toDTO(Cliente cliente);

    /**
     * Converte lista de Clientes para lista de ClienteDTOs
     */
    List<ClienteDTO> toDTO(List<Cliente> clientes);

    /**
     * Atualiza uma entidade Cliente existente com dados do DTO
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enderecos", ignore = true) // Endereços são tratados separadamente no service
    void updateClienteFromDto(ClienteRequestBody dto, @MappingTarget Cliente cliente);

    /**
     * Converte Page de Cliente para Page de ClienteDTO
     */
    default org.springframework.data.domain.Page<ClienteDTO> toDTO(Page<Cliente> clientePage) {
        return clientePage.map(this::toDTO);
    }
}
