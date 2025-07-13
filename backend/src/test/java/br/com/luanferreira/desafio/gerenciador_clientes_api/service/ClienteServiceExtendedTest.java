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

    @InjectMocks
    private ClienteApplicationService clienteService;

    @Test
    @DisplayName("Deve lançar exceção ao tentar criar cliente com CPF já existente")
    void criarCliente_deveLancarExcecao_quandoCpfJaExiste() {
        // Given
        var clienteRequest = criarClienteRequestValido();
        when(clienteRepository.findByCpf(clienteRequest.getCpf()))
                .thenReturn(Optional.of(new Cliente()));

        // When & Then
        var exception = assertThrows(CpfJaCadastradoException.class, () -> {
            clienteService.criarCliente(clienteRequest);
        });

        assertEquals("CPF 11144477735 já cadastrado.", exception.getMessage());
        verify(clienteRepository, never()).save(any(Cliente.class));
    }

    @Test
    @DisplayName("Deve criar cliente com sucesso quando dados são válidos")
    void criarCliente_deveCriarComSucesso_quandoDadosValidos() {
        // Given
        var clienteRequest = criarClienteRequestValido();
        var clienteSalvo = criarClienteComId();
        var enderecoViaCep = new EnderecoDTO("01001000", "Praça da Sé", "Sé", "São Paulo", "SP", null);

        when(clienteRepository.findByCpf(anyString())).thenReturn(Optional.empty());
        when(viaCepClient.consultarCep(anyString())).thenReturn(enderecoViaCep);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteSalvo);

        // When
        var resultado = clienteService.criarCliente(clienteRequest);

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
        var cliente = criarClienteComId();
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        // When
        var resultado = clienteService.buscarPorId(1L);

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
        var exception = assertThrows(ClienteNaoEncontradoException.class, () -> {
            clienteService.buscarPorId(999L);
        });

        assertEquals("Cliente com ID 999 não encontrado.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve listar clientes com paginação")
    void listarTodos_deveRetornarPaginaClientes() {
        // Given
        var cliente = criarClienteComId();
        var pageable = PageRequest.of(0, 10);
        var clientesPage = new PageImpl<>(List.of(cliente), pageable, 1);

        when(clienteRepository.findAll((Specification<Cliente>) any(), eq(pageable))).thenReturn(clientesPage);

        // When
        var resultado = clienteService.listarTodos(null, null, pageable);

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
        var clienteExistente = criarClienteComId();
        var clienteRequest = criarClienteRequestValido();
        clienteRequest.setNome("João Silva Atualizado");
        
        var enderecoViaCep = new EnderecoDTO("01001000", "Praça da Sé", "Sé", "São Paulo", "SP", null);

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteExistente));
        when(clienteRepository.findByCpf(anyString())).thenReturn(Optional.of(clienteExistente));
        when(viaCepClient.consultarCep(anyString())).thenReturn(enderecoViaCep);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteExistente);

        // When
        var resultado = clienteService.atualizarCliente(1L, clienteRequest);

        // Then
        assertNotNull(resultado);
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar deletar cliente inexistente")
    void deletarCliente_deveLancarExcecao_quandoClienteNaoExiste() throws Exception {
        // Given
        when(clienteRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        var exception = assertThrows(ClienteNaoEncontradoException.class, () -> {
            clienteService.deletarCliente(999L);
        });

        assertEquals("Cliente com ID 999 não encontrado.", exception.getMessage());
        verify(clienteRepository, never()).delete(any(Cliente.class));
    }

    // Métodos auxiliares para criação de objetos de teste
    private ClienteRequestBody criarClienteRequestValido() {
        var telefone = new Telefone("11", "987654321", "CELULAR");
        var email = new Email("joao@teste.com");
        var endereco = new EnderecoDTO("01001000", null, null, null, null, "Apto 101");

        return new ClienteRequestBody(
                "João Silva",
                "11144477735",
                Set.of(telefone),
                Set.of(email),
                List.of(endereco)
        );
    }

    private Cliente criarClienteComId() {
        var cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("João Silva");
        cliente.setCpf("11144477735");
        cliente.setTelefones(Set.of(new Telefone("11", "987654321", "CELULAR")));
        cliente.setEmails(Set.of(new Email("joao@teste.com")));
        return cliente;
    }
}
