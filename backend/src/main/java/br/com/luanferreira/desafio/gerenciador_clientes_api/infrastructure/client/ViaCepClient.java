package br.com.luanferreira.desafio.gerenciador_clientes_api.infrastructure.client;

import br.com.luanferreira.desafio.gerenciador_clientes_api.application.dto.EnderecoDTO;
import br.com.luanferreira.desafio.gerenciador_clientes_api.domain.exception.ViaCepException;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ViaCepClient {

    private final WebClient webClient;

    public ViaCepClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://viacep.com.br/ws").build();
    }

    public EnderecoDTO consultarCep(String cep) {
        return webClient.get()
                .uri("/{cep}/json", cep)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response -> Mono.error(new ViaCepException("Erro ao consultar CEP: " + cep)))
                .bodyToMono(EnderecoDTO.class)
                .block();
    }
}
