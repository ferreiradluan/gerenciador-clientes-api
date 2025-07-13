package br.com.luanferreira.desafio.gerenciador_clientes_api.service;

import br.com.luanferreira.desafio.gerenciador_clientes_api.application.dto.ClienteDTO;
import br.com.luanferreira.desafio.gerenciador_clientes_api.application.dto.ClienteRequestBody;
import br.com.luanferreira.desafio.gerenciador_clientes_api.application.mapper.ClienteMapper;
import br.com.luanferreira.desafio.gerenciador_clientes_api.application.service.ClienteApplicationService;
import br.com.luanferreira.desafio.gerenciador_clientes_api.domain.exception.CpfJaCadastradoException;
import br.com.luanferreira.desafio.gerenciador_clientes_api.domain.model.Cliente;
import br.com.luanferreira.desafio.gerenciador_clientes_api.domain.repository.ClienteRepository;
import br.com.luanferreira.desafio.gerenciador_clientes_api.infrastructure.client.ViaCepClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// Setup de classe de teste para ClienteService usando JUnit 5 e Mockito
@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

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
    void criar_deveLancarExcecao_quandoCpfJaExiste() {
        // given: Um DTO de cliente e um CPF que já sabemos que existe no banco
        var dto = new ClienteRequestBody();
        dto.setCpf("12345678901");
        when(clienteRepository.findByCpf(dto.getCpf())).thenReturn(Optional.of(new Cliente()));

        // when & then: Verificamos se a exceção correta é lançada
        // assert that calling criar method throws CpfJaCadastradoException with a specific message
        var exception = assertThrows(CpfJaCadastradoException.class, () -> {
            clienteService.criarCliente(dto);
        });

        assertEquals("CPF 12345678901 já cadastrado.", exception.getMessage());

        // Garantimos que o método save nunca foi chamado
        verify(clienteRepository, never()).save(any(Cliente.class));
    }

    @Test
    @DisplayName("Deve criar um cliente com sucesso quando os dados são válidos")
    void criar_deveCriarCliente_quandoDadosValidos() {
        // given: Um DTO de cliente válido
        // Complete o teste para o caminho feliz do método criar cliente, mockando o mapper e o save do repositório
        var dto = new ClienteRequestBody();
        dto.setCpf("11122233344");
        dto.setNome("João Silva");
        dto.setEnderecos(List.of()); // Adicionando lista vazia de endereços
        
        var clienteMapeado = new Cliente();
        var clienteSalvo = new Cliente();
        clienteSalvo.setId(1L);
        
        var clienteDTO = new ClienteDTO();
        clienteDTO.setId(1L);
        clienteDTO.setNome("João Silva");

        when(clienteRepository.findByCpf(dto.getCpf())).thenReturn(Optional.empty());
        when(clienteMapper.toEntity(dto)).thenReturn(clienteMapeado);
        when(clienteRepository.save(clienteMapeado)).thenReturn(clienteSalvo);
        when(clienteMapper.toDTO(clienteSalvo)).thenReturn(clienteDTO);

        // when
        var clienteResponse = clienteService.criarCliente(dto);

        // then
        assertNotNull(clienteResponse);
        verify(clienteRepository, times(1)).save(clienteMapeado);
        verify(clienteMapper, times(1)).toEntity(dto);
        verify(clienteMapper, times(1)).toDTO(clienteSalvo);
    }
}
