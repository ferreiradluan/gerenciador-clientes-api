package br.com.luanferreira.desafio.gerenciador_clientes_api.application.controller;

import br.com.luanferreira.desafio.gerenciador_clientes_api.application.dto.ClienteDTO;
import br.com.luanferreira.desafio.gerenciador_clientes_api.application.dto.ClienteRequestBody;
import br.com.luanferreira.desafio.gerenciador_clientes_api.application.service.ClienteApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Clientes", description = "API para Gerenciamento de Clientes")
public class ClienteController {

    private final ClienteApplicationService clienteApplicationService;

    @PostMapping
    @Operation(summary = "Cria um novo cliente", description = "Cria um novo cliente no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    public ResponseEntity<ClienteDTO> criarCliente(@Valid @RequestBody ClienteRequestBody clienteRequestBody) {
        ClienteDTO clienteCriado = clienteApplicationService.criarCliente(clienteRequestBody);
        return new ResponseEntity<>(clienteCriado, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Lista todos os clientes com paginação e filtros", description = "Retorna uma lista paginada de clientes, com filtros opcionais por nome e CPF")
    public ResponseEntity<Page<ClienteDTO>> listarTodos(
            @Parameter(description = "Nome do cliente para filtro") @RequestParam(required = false) String nome,
            @Parameter(description = "CPF do cliente para filtro") @RequestParam(required = false) String cpf,
            Pageable pageable) {
        Page<ClienteDTO> clientes = clienteApplicationService.listarTodos(nome, cpf, pageable);
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um cliente por ID", description = "Retorna um cliente específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    public ResponseEntity<ClienteDTO> buscarPorId(@PathVariable Long id) {
        ClienteDTO cliente = clienteApplicationService.buscarPorId(id);
        return ResponseEntity.ok(cliente);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um cliente", description = "Atualiza os dados de um cliente existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    public ResponseEntity<ClienteDTO> atualizarCliente(@PathVariable Long id, @Valid @RequestBody ClienteRequestBody clienteRequestBody) {
        ClienteDTO clienteAtualizado = clienteApplicationService.atualizarCliente(id, clienteRequestBody);
        return ResponseEntity.ok(clienteAtualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um cliente", description = "Remove um cliente do sistema pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    public ResponseEntity<Void> deletarCliente(@PathVariable Long id) {
        clienteApplicationService.deletarCliente(id);
        return ResponseEntity.noContent().build();
    }
}
