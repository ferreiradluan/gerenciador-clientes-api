package br.com.luanferreira.desafio.gerenciador_clientes_api.service;

import br.com.luanferreira.desafio.gerenciador_clientes_api.application.dto.ClienteDTO;
import br.com.luanferreira.desafio.gerenciador_clientes_api.application.dto.ClienteRequestBody;
import br.com.luanferreira.desafio.gerenciador_clientes_api.application.dto.EnderecoDTO;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteApplicationServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ViaCepClient viaCepClient;

    @InjectMocks
    private ClienteApplicationService clienteApplicationService;

    @Test
    void criarCliente_ComCpfNaoExistente_DeveCriarCliente() {
        ClienteRequestBody requestBody = new ClienteRequestBody("Nome", "12345678901", Collections.singleton(new Telefone("11", "999999999", "Celular")),
                Collections.singleton(new Email("test@test.com")), Collections.singletonList(new EnderecoDTO("01001000", null, null, null, null, "Apto 1")));
        when(clienteRepository.findByCpf(any())).thenReturn(Optional.empty());
        when(viaCepClient.consultarCep(any())).thenReturn(new EnderecoDTO("01001000", "Praça da Sé", "Sé", "São Paulo", "SP", null));
        when(clienteRepository.save(any())).thenReturn(new Cliente());

        ClienteDTO result = clienteApplicationService.criarCliente(requestBody);

        assertNotNull(result);
        verify(clienteRepository, times(1)).save(any());
    }

    @Test
    void criarCliente_ComCpfExistente_DeveLancarExcecao() {
        ClienteRequestBody requestBody = new ClienteRequestBody("Nome", "12345678901", null, null, null);
        when(clienteRepository.findByCpf(any())).thenReturn(Optional.of(new Cliente()));

        assertThrows(CpfJaCadastradoException.class, () -> clienteApplicationService.criarCliente(requestBody));
    }

    @Test
    void listarTodos_DeveRetornarPaginaDeClientes() {
        when(clienteRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(Collections.singletonList(new Cliente())));

        Page<ClienteDTO> result = clienteApplicationService.listarTodos(null, null, Pageable.unpaged());

        assertEquals(1, result.getTotalElements());
    }

    @Test
    void buscarPorId_ComIdExistente_DeveRetornarCliente() {
        when(clienteRepository.findById(anyLong())).thenReturn(Optional.of(new Cliente()));

        ClienteDTO result = clienteApplicationService.buscarPorId(1L);

        assertNotNull(result);
    }

    @Test
    void buscarPorId_ComIdNaoExistente_DeveLancarExcecao() {
        when(clienteRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ClienteNaoEncontradoException.class, () -> clienteApplicationService.buscarPorId(1L));
    }
}
