package br.com.luanferreira.desafio.gerenciador_clientes_api.controller;

import br.com.luanferreira.desafio.gerenciador_clientes_api.application.dto.ClienteRequestBody;
import br.com.luanferreira.desafio.gerenciador_clientes_api.application.dto.EnderecoDTO;
import br.com.luanferreira.desafio.gerenciador_clientes_api.domain.model.Email;
import br.com.luanferreira.desafio.gerenciador_clientes_api.domain.model.Telefone;
import br.com.luanferreira.desafio.gerenciador_clientes_api.infrastructure.client.ViaCepClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ClienteControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ViaCepClient viaCepClient;

    @Test
    @WithMockUser(roles = "ADMIN")
    void criarCliente_ComDadosValidos_DeveRetornarCreated() throws Exception {
        // Mock do ViaCEP
        when(viaCepClient.consultarCep(any())).thenReturn(
                new EnderecoDTO("01001000", "Praça da Sé", "Sé", "São Paulo", "SP", null)
        );

        // Usando CPF válido para teste
        ClienteRequestBody requestBody = new ClienteRequestBody("Nome Teste", "11144477735",
                Collections.singleton(new Telefone("11", "987654321", "CELULAR")),
                Collections.singleton(new Email("teste@teste.com")),
                Collections.singletonList(new EnderecoDTO("01001000", null, null, null, null, "Apto 1")));

        mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isCreated());
    }
}
