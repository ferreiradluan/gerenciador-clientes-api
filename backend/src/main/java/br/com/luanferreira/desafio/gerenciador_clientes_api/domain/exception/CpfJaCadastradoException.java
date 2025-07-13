package br.com.luanferreira.desafio.gerenciador_clientes_api.domain.exception;

public class CpfJaCadastradoException extends RuntimeException {

    public CpfJaCadastradoException(String cpf) {
        super("CPF " + cpf + " já cadastrado.");
    }
}
