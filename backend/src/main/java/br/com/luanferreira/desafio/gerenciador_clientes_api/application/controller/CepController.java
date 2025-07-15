package br.com.luanferreira.desafio.gerenciador_clientes_api.application.controller;

import br.com.luanferreira.desafio.gerenciador_clientes_api.application.dto.EnderecoDTO;
import br.com.luanferreira.desafio.gerenciador_clientes_api.application.util.CepUtil;
import br.com.luanferreira.desafio.gerenciador_clientes_api.infrastructure.client.ViaCepClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cep")
@RequiredArgsConstructor
@Tag(name = "CEP", description = "API para consulta de CEP via ViaCEP")
public class CepController {

    private final ViaCepClient viaCepClient;

    @GetMapping("/{cep}")
    @Operation(summary = "Consulta dados do CEP", description = "Consulta informações de endereço através do CEP usando o serviço ViaCEP")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CEP encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "CEP não encontrado"),
            @ApiResponse(responseCode = "400", description = "CEP inválido")
    })
    public ResponseEntity<EnderecoDTO> consultarCep(
            @Parameter(description = "CEP a ser consultado (com ou sem máscara)")
            @PathVariable String cep) {
        
        String cepLimpo = CepUtil.removerMascara(cep);
        
        // Validação do CEP
        if (!CepUtil.isValido(cepLimpo)) {
            return ResponseEntity.badRequest().build();
        }
        
        EnderecoDTO endereco = viaCepClient.consultarCep(cepLimpo);
        
        // Aplica máscara no CEP para retorno
        endereco.setCep(CepUtil.aplicarMascara(cepLimpo));
        
        return ResponseEntity.ok(endereco);
    }
}
