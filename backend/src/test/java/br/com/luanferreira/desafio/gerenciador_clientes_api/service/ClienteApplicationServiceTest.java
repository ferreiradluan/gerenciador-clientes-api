package br.com.luanferreira.desafio.gerenciador_clientes_api.service;

import br.com.luanferreira.desafio.gerenciador_clientes_api.application.dto.ClienteDTO;
import br.com.luanferreira.desafio.gerenciador_clientes_api.application.dto.ClienteRequestBody;
import br.com.luanferreira.desafio.gerenciador_clientes_api.application.dto.EnderecoDTO;
import br.com.luanferreira.desafio.gerenciador_clientes_api.application.mapper.ClienteMapper;
import br.com.luanferreira.desafio.gerenciador_clientes_api.application.service.ClienteApplicationService;
import br.com.luanferreira.desafio.gerenciador_clientes_api.domain.exception.ClienteNaoEncontradoException;
import br.com.luanferreira.desafio.gerenciador_clientes_api.domain.exception.CpfJaCadastradoException;
import br.com.luanferreira.desafio.gerenciador_clientes_api.domain.model.Cliente;
import br.com.luanferreira.desafio.gerenciador_clientes_api.domain.model.Email;
import br.com.luanferreira.desafio.gerenciador_clientes_api.domain.model.Telefone;
import br.com.luanferreira.desafio.gerenciador_clientes_api.domain.repository.ClienteRepository;
import br.com.luanferreira.desafio.gerenciador_clientes_api.infrastructure.client.ViaCepClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ClienteApplicationServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ViaCepClient viaCepClient;

    @Mock
    private ClienteMapper clienteMapper;

    @InjectMocks
    private ClienteApplicationService clienteApplicationService;

    @Test
    void criarCliente_ComCpfNaoExistente_DeveCriarCliente() {
        // CPF válido para teste
        ClienteRequestBody requestBody = new ClienteRequestBody("Nome", "11144477735", Collections.singleton(new Telefone("11", "999999999", "Celular")),
                Collections.singleton(new Email("test@test.com")), Collections.singletonList(new EnderecoDTO("01001000", null, null, null, null, "Apto 1")));
        Cliente cliente = new Cliente();
        ClienteDTO clienteDTO = new ClienteDTO();
        
        when(clienteRepository.findByCpf(any())).thenReturn(Optional.empty());
        when(clienteMapper.toEntity(requestBody)).thenReturn(cliente);
        when(viaCepClient.consultarCep(any())).thenReturn(new EnderecoDTO("01001000", "Praça da Sé", "Sé", "São Paulo", "SP", null));
        when(clienteRepository.save(any())).thenReturn(cliente);
        when(clienteMapper.toDTO(cliente)).thenReturn(clienteDTO);

        ClienteDTO result = clienteApplicationService.criarCliente(requestBody);

        assertNotNull(result);
        verify(clienteRepository, times(1)).save(any());
        verify(clienteMapper, times(1)).toEntity(requestBody);
        verify(clienteMapper, times(1)).toDTO(cliente);
    }

    @Test
    void criarCliente_ComCpfExistente_DeveLancarExcecao() {
        ClienteRequestBody requestBody = new ClienteRequestBody("Nome", "11144477735", null, null, null);
        when(clienteRepository.findByCpf(any())).thenReturn(Optional.of(new Cliente()));

        assertThrows(CpfJaCadastradoException.class, () -> clienteApplicationService.criarCliente(requestBody));
    }

    @Test
    void listarTodos_DeveRetornarPaginaDeClientes() {
        // Given
        var cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("João Silva");
        cliente.setCpf("11144477735");
        
        var clienteDTO = new ClienteDTO();
        clienteDTO.setId(1L);
        clienteDTO.setNome("João Silva");
        clienteDTO.setCpf("11144477735");
        
        var clientesPage = new PageImpl<>(Collections.singletonList(cliente));
        var clientesDTOPage = new PageImpl<>(Collections.singletonList(clienteDTO));

        when(clienteRepository.findAll(any(org.springframework.data.jpa.domain.Specification.class), any(Pageable.class)))
                .thenReturn(clientesPage);
        when(clienteMapper.toDTO(clientesPage)).thenReturn(clientesDTOPage);

        // When
        Page<ClienteDTO> result = clienteApplicationService.listarTodos(null, null, Pageable.unpaged());

        // Then
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void buscarPorId_ComIdExistente_DeveRetornarCliente() {
        // Given
        var cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("João Silva");
        cliente.setCpf("11144477735");
        
        var clienteDTO = new ClienteDTO();
        clienteDTO.setId(1L);
        clienteDTO.setNome("João Silva");
        clienteDTO.setCpf("11144477735");

        when(clienteRepository.findById(anyLong())).thenReturn(Optional.of(cliente));
        when(clienteMapper.toDTO(cliente)).thenReturn(clienteDTO);

        // When
        ClienteDTO result = clienteApplicationService.buscarPorId(1L);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("João Silva", result.getNome());
    }

    @Test
    void buscarPorId_ComIdNaoExistente_DeveLancarExcecao() {
        when(clienteRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ClienteNaoEncontradoException.class, () -> clienteApplicationService.buscarPorId(1L));
    }
}
