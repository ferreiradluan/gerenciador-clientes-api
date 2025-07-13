package br.com.luanferreira.desafio.gerenciador_clientes_api.infrastructure.config;

import br.com.luanferreira.desafio.gerenciador_clientes_api.domain.exception.ClienteNaoEncontradoException;
import br.com.luanferreira.desafio.gerenciador_clientes_api.domain.exception.CpfJaCadastradoException;
import br.com.luanferreira.desafio.gerenciador_clientes_api.domain.exception.ViaCepException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.Map;

/**
 * Classe de advice para tratamento de exceções globais na API
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

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
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("erro", "Ocorreu um erro inesperado no servidor."));
    }
}
