package br.com.luanferreira.desafio.gerenciador_clientes_api.infrastructure.config;

import br.com.luanferreira.desafio.gerenciador_clientes_api.domain.model.Usuario;
import br.com.luanferreira.desafio.gerenciador_clientes_api.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        log.info("Iniciando inicialização de dados...");
        
        try {
            // Cria o usuário ADMIN se ele não existir
            if (usuarioRepository.findByLogin("admin") == null) {
                log.info("Criando usuário admin...");
                Usuario admin = new Usuario();
                admin.setLogin("admin");
                // Senha do desafio: 123qwe!@#
                admin.setSenha(passwordEncoder.encode("123qwe!@#"));
                admin.setRole("ADMIN");
                usuarioRepository.save(admin);
                log.info("Usuário admin criado com sucesso");
            } else {
                log.info("Usuário admin já existe");
            }

            // Cria o usuário PADRÃO se ele não existir
            if (usuarioRepository.findByLogin("user") == null) {
                log.info("Criando usuário user...");
                Usuario user = new Usuario();
                user.setLogin("user");
                // Senha do desafio: 123qwe123
                user.setSenha(passwordEncoder.encode("123qwe123"));
                user.setRole("USER");
                usuarioRepository.save(user);
                log.info("Usuário user criado com sucesso");
            } else {
                log.info("Usuário user já existe");
            }
            
            log.info("Inicialização de dados concluída com sucesso");
        } catch (Exception e) {
            log.error("Erro durante a inicialização de dados: {}", e.getMessage(), e);
            throw e;
        }
    }
}
