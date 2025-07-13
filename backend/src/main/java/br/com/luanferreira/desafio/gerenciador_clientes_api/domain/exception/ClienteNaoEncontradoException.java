package br.com.luanferreira.desafio.gerenciador_clientes_api.domain.exception;

public class ClienteNaoEncontradoException extends RuntimeException {

    public ClienteNaoEncontradoException(Long id) {
        super("Cliente com ID " + id + " não encontrado.");
    }

    public ClienteNaoEncontradoException(String cpf) {
        super("Cliente com CPF " + cpf + " não encontrado.");
    }
}
