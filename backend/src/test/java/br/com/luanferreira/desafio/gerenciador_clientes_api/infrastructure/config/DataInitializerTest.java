package br.com.luanferreira.desafio.gerenciador_clientes_api.infrastructure.config;

import br.com.luanferreira.desafio.gerenciador_clientes_api.domain.model.Usuario;
import br.com.luanferreira.desafio.gerenciador_clientes_api.domain.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DataInitializerTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private DataInitializer dataInitializer;

    @Test
    void run_DevecriarUsuariosPadraoQuandoNaoExistirem() throws Exception {
        // Given
        when(usuarioRepository.findByLogin("admin")).thenReturn(null);
        when(usuarioRepository.findByLogin("user")).thenReturn(null);
        when(passwordEncoder.encode("123qwe!@#")).thenReturn("encoded_admin_password");
        when(passwordEncoder.encode("123qwe123")).thenReturn("encoded_user_password");

        // When
        dataInitializer.run();

        // Then
        verify(usuarioRepository, times(2)).save(any(Usuario.class));
        verify(passwordEncoder).encode("123qwe!@#");
        verify(passwordEncoder).encode("123qwe123");
    }

    @Test
    void run_NaoDeveCriarUsuariosQuandoJaExistirem() throws Exception {
        // Given
        Usuario adminExistente = new Usuario();
        adminExistente.setLogin("admin");
        
        Usuario userExistente = new Usuario();
        userExistente.setLogin("user");

        when(usuarioRepository.findByLogin("admin")).thenReturn(adminExistente);
        when(usuarioRepository.findByLogin("user")).thenReturn(userExistente);

        // When
        dataInitializer.run();

        // Then
        verify(usuarioRepository, never()).save(any(Usuario.class));
        verify(passwordEncoder, never()).encode(anyString());
    }
}
