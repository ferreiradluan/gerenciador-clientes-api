package br.com.luanferreira.desafio.gerenciador_clientes_api.domain.repository;

import br.com.luanferreira.desafio.gerenciador_clientes_api.domain.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>, JpaSpecificationExecutor<Cliente> {

    Optional<Cliente> findByCpf(String cpf);
}
