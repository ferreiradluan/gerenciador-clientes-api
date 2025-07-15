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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Testes unitários estendidos para ClienteApplicationService
 * Foco em cenários de validação e edge cases
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ClienteServiceExtendedTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ViaCepClient viaCepClient;

    @Mock
    private ClienteMapper clienteMapper;

    @InjectMocks
    private ClienteApplicationService clienteService;

    @Test
    @DisplayName("Deve lançar exceção ao tentar criar cliente com CPF já existente")
    void criarCliente_deveLancarExcecao_quandoCpfJaExiste() {
        // Given
        ClienteRequestBody clienteRequest = criarClienteRequestValido();
        when(clienteRepository.findByCpf(clienteRequest.getCpf()))
                .thenReturn(Optional.of(new Cliente()));

        // When & Then
        CpfJaCadastradoException exception = assertThrows(CpfJaCadastradoException.class, () -> {
            clienteService.criarCliente(clienteRequest);
        });

        assertEquals("CPF 11144477735 já cadastrado.", exception.getMessage());
        verify(clienteRepository, never()).save(any(Cliente.class));
    }

    @Test
    @DisplayName("Deve criar cliente com sucesso quando dados são válidos")
    void criarCliente_deveCriarComSucesso_quandoDadosValidos() {
        // Given
        ClienteRequestBody clienteRequest = criarClienteRequestValido();
        Cliente clienteSalvo = criarClienteComId();
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setId(1L);
        clienteDTO.setNome("João Silva");
        clienteDTO.setCpf("11144477735");
        EnderecoDTO enderecoViaCep = new EnderecoDTO("01001000", "Praça da Sé", "Sé", "São Paulo", "SP", null);

        when(clienteRepository.findByCpf(anyString())).thenReturn(Optional.empty());
        when(clienteMapper.toEntity(clienteRequest)).thenReturn(clienteSalvo);
        when(viaCepClient.consultarCep(anyString())).thenReturn(enderecoViaCep);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteSalvo);
        when(clienteMapper.toDTO(clienteSalvo)).thenReturn(clienteDTO);

        // When
        ClienteDTO resultado = clienteService.criarCliente(clienteRequest);

        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("João Silva", resultado.getNome());
        assertEquals("11144477735", resultado.getCpf());
        verify(clienteRepository, times(1)).save(any(Cliente.class));
        verify(viaCepClient, times(1)).consultarCep("01001000");
    }

    @Test
    @DisplayName("Deve buscar cliente por ID com sucesso")
    void buscarPorId_deveRetornarCliente_quandoIdExiste() {
        // Given
        Cliente cliente = criarClienteComId();
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setId(1L);
        clienteDTO.setNome("João Silva");
        
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(clienteMapper.toDTO(cliente)).thenReturn(clienteDTO);

        // When
        ClienteDTO resultado = clienteService.buscarPorId(1L);

        // Then
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("João Silva", resultado.getNome());
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar cliente inexistente")
    void buscarPorId_deveLancarExcecao_quandoIdNaoExiste() {
        // Given
        when(clienteRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        ClienteNaoEncontradoException exception = assertThrows(ClienteNaoEncontradoException.class, () -> {
            clienteService.buscarPorId(999L);
        });

        assertEquals("Cliente com ID 999 não encontrado.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve listar clientes com paginação")
    void listarTodos_deveRetornarPaginaClientes() {
        // Given
        Cliente cliente = criarClienteComId();
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setNome("João Silva");
        Pageable pageable = PageRequest.of(0, 10);
        Page<Cliente> clientesPage = new PageImpl<>(Collections.singletonList(cliente), pageable, 1);
        Page<ClienteDTO> clientesDTOPage = new PageImpl<>(Collections.singletonList(clienteDTO), pageable, 1);

        when(clienteRepository.findAll((Specification<Cliente>) any(), eq(pageable))).thenReturn(clientesPage);
        when(clienteMapper.toDTO(clientesPage)).thenReturn(clientesDTOPage);

        // When
        Page<ClienteDTO> resultado = clienteService.listarTodos(null, null, pageable);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.getTotalElements());
        assertEquals(1, resultado.getContent().size());
        assertEquals("João Silva", resultado.getContent().get(0).getNome());
    }

    @Test
    @DisplayName("Deve atualizar cliente existente")
    void atualizarCliente_deveAtualizarComSucesso_quandoClienteExiste() {
        // Given
        Cliente clienteExistente = criarClienteComId();
        ClienteRequestBody clienteRequest = criarClienteRequestValido();
        clienteRequest.setNome("João Silva Atualizado");
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setId(1L);
        
        EnderecoDTO enderecoViaCep = new EnderecoDTO("01001000", "Praça da Sé", "Sé", "São Paulo", "SP", null);

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteExistente));
        when(clienteRepository.findByCpf(anyString())).thenReturn(Optional.of(clienteExistente));
        when(viaCepClient.consultarCep(anyString())).thenReturn(enderecoViaCep);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteExistente);
        when(clienteMapper.toDTO(clienteExistente)).thenReturn(clienteDTO);

        // When
        ClienteDTO resultado = clienteService.atualizarCliente(1L, clienteRequest);

        // Then
        assertNotNull(resultado);
        verify(clienteRepository, times(1)).save(any(Cliente.class));
        verify(clienteMapper, times(1)).updateClienteFromDto(clienteRequest, clienteExistente);
    }

    @Test
    @DisplayName("Deve deletar cliente com sucesso quando cliente existe")
    void deletarCliente_deveDeletarComSucesso_quandoClienteExiste() {
        // Given
        when(clienteRepository.existsById(1L)).thenReturn(true);

        // When & Then
        assertDoesNotThrow(() -> {
            clienteService.deletarCliente(1L);
        });

        verify(clienteRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar deletar cliente inexistente")
    void deletarCliente_deveLancarExcecao_quandoClienteNaoExiste() throws Exception {
        // Given
        when(clienteRepository.existsById(999L)).thenReturn(false);

        // When & Then
        ClienteNaoEncontradoException exception = assertThrows(ClienteNaoEncontradoException.class, () -> {
            clienteService.deletarCliente(999L);
        });

        assertEquals("Cliente com ID 999 não encontrado.", exception.getMessage());
        verify(clienteRepository, never()).deleteById(any(Long.class));
    }

    // Métodos auxiliares para criação de objetos de teste
    private ClienteRequestBody criarClienteRequestValido() {
        Telefone telefone = new Telefone("11", "987654321", "CELULAR");
        Email email = new Email("joao@teste.com");
        EnderecoDTO endereco = new EnderecoDTO("01001000", null, null, null, null, "Apto 101");

        return new ClienteRequestBody(
                "João Silva",
                "11144477735",
                new HashSet<>(Collections.singletonList(telefone)),
                new HashSet<>(Collections.singletonList(email)),
                Collections.singletonList(endereco)
        );
    }

    private Cliente criarClienteComId() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("João Silva");
        cliente.setCpf("11144477735");
        cliente.setTelefones(new HashSet<>(Collections.singletonList(new Telefone("11", "987654321", "CELULAR"))));
        cliente.setEmails(new HashSet<>(Collections.singletonList(new Email("joao@teste.com"))));
        return cliente;
    }
}
