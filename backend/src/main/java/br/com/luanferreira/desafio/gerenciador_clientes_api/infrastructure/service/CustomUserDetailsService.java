package br.com.luanferreira.desafio.gerenciador_clientes_api.infrastructure.service;

import br.com.luanferreira.desafio.gerenciador_clientes_api.domain.model.Usuario;
import br.com.luanferreira.desafio.gerenciador_clientes_api.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByLogin(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado: " + username);
        }

        return User.builder()
                .username(usuario.getLogin())
                .password(usuario.getSenha())
                .roles(usuario.getRole())
                .build();
    }
}
