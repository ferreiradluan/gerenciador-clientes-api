package br.com.luanferreira.desafio.gerenciador_clientes_api.infrastructure.config;

import br.com.luanferreira.desafio.gerenciador_clientes_api.domain.exception.ClienteNaoEncontradoException;
import br.com.luanferreira.desafio.gerenciador_clientes_api.domain.exception.CpfJaCadastradoException;
import br.com.luanferreira.desafio.gerenciador_clientes_api.domain.exception.ViaCepException;
import javax.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe de advice para tratamento de exceções globais na API
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Handler para exceções de autenticação
     */
    @ExceptionHandler({AuthenticationException.class, BadCredentialsException.class})
    public ResponseEntity<Map<String, String>> handleAuthenticationException(Exception ex) {
        log.warn("Tentativa de autenticação falhada: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Collections.singletonMap("erro", "Credenciais inválidas"));
    }

    /**
     * Handler para validação de campos
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errors);
    }

    /**
     * Handler para exceção de CPF já cadastrado
     */
    @ExceptionHandler(CpfJaCadastradoException.class)
    public ResponseEntity<Map<String, String>> handleCpfJaCadastradoException(CpfJaCadastradoException ex) {
        return ResponseEntity.badRequest().body(Collections.singletonMap("erro", ex.getMessage()));
    }

    /**
     * Handler para exceção de cliente não encontrado
     */
    @ExceptionHandler(ClienteNaoEncontradoException.class)
    public ResponseEntity<Map<String, String>> handleClienteNaoEncontradoException(ClienteNaoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("erro", ex.getMessage()));
    }

    /**
     * Handler para exceção do ViaCEP
     */
    @ExceptionHandler(ViaCepException.class)
    public ResponseEntity<Map<String, String>> handleViaCepException(ViaCepException ex) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(Collections.singletonMap("erro", ex.getMessage()));
    }

    /**
     * Handler para EntityNotFoundException do JPA
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleEntityNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("erro", "Recurso não encontrado."));
    }

    /**
     * Handler genérico para qualquer outra exceção não tratada
     * NÃO intercepta exceções de autenticação (já tratadas acima)
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        log.error("Erro inesperado no servidor: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Collections.singletonMap("erro", "Ocorreu um erro inesperado no servidor."));
    }
}
