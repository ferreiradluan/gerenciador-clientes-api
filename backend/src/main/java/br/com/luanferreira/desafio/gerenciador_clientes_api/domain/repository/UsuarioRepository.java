package br.com.luanferreira.desafio.gerenciador_clientes_api.domain.repository;

import br.com.luanferreira.desafio.gerenciador_clientes_api.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByLogin(String login);
}
