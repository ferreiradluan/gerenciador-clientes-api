package br.com.luanferreira.desafio.gerenciador_clientes_api.domain.repository;

import br.com.luanferreira.desafio.gerenciador_clientes_api.domain.model.Cliente;
import org.springframework.data.jpa.domain.Specification;

public class ClienteSpecification {

    public static Specification<Cliente> comNome(String nome) {
        return (root, query, criteriaBuilder) ->
                nome == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
    }

    public static Specification<Cliente> comCpf(String cpf) {
        return (root, query, criteriaBuilder) ->
                cpf == null ? null : criteriaBuilder.equal(root.get("cpf"), cpf);
    }
}
