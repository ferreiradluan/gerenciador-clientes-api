package br.com.luanferreira.desafio.gerenciador_clientes_api.infrastructure.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Gerenciador de Clientes API", version = "v1", description = "API para gerenciamento de clientes"))
public class OpenApiConfig {
}
