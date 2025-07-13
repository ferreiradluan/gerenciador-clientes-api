package br.com.luanferreira.desafio.gerenciador_clientes_api.controller;

import br.com.luanferreira.desafio.gerenciador_clientes_api.infrastructure.client.ViaCepClient;
import br.com.luanferreira.desafio.gerenciador_clientes_api.util.TestDataBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Testes de integração estendidos para ClienteController
 * Testa cenários completos end-to-end
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class ClienteControllerExtendedIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ViaCepClient viaCepClient;

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Deve retornar 400 ao tentar criar cliente com CPF inválido")
    void criarCliente_deveRetornar400_quandoCpfInvalido() throws Exception {
        // Given
        var clienteRequest = TestDataBuilder.DadosInvalidos.comCpfInvalido();

        // When & Then
        mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.cpf").exists());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Deve retornar 400 ao tentar criar cliente com nome vazio")
    void criarCliente_deveRetornar400_quandoNomeVazio() throws Exception {
        // Given
        var clienteRequest = TestDataBuilder.DadosInvalidos.comNomeVazio();

        // When & Then
        mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.nome").exists());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Deve listar clientes com paginação")
    void listarClientes_deveRetornarPaginacao_comSucesso() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/clientes")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "nome,asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.pageable").exists())
                .andExpect(jsonPath("$.totalElements").exists());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Deve filtrar clientes por nome")
    void listarClientes_deveFiltrarPorNome_comSucesso() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/clientes")
                        .param("nome", "João"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Deve retornar 404 ao buscar cliente inexistente")
    void buscarCliente_deveRetornar404_quandoNaoExiste() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/clientes/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.erro").value("Cliente com ID 999 não encontrado."));
    }

    @Test
    @DisplayName("Deve retornar 403 quando não autenticado")
    void acessarEndpoint_deveRetornar403_quandoNaoAutenticado() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/clientes"))
                .andExpect(status().isForbidden());
    }
}
