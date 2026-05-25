package com.gruposolutia.gestion_proyectos.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.gruposolutia.gestion_proyectos.model.Usuario;
import com.gruposolutia.gestion_proyectos.repository.UsuarioRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (usuarioRepository.count() > 0) {
            return;
        }

        Usuario admin = new Usuario();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("1234"));
        admin.setRole("ROLE_ADMIN");
        usuarioRepository.save(admin);

        Usuario tecnico = new Usuario();
        tecnico.setUsername("tecnico");
        tecnico.setPassword(passwordEncoder.encode("1234"));
        tecnico.setRole("ROLE_TECNICO");
        usuarioRepository.save(tecnico);

        Usuario visitante = new Usuario();
        visitante.setUsername("visitante");
        visitante.setPassword(passwordEncoder.encode("1234"));
        visitante.setRole("ROLE_VISITANTE");
        usuarioRepository.save(visitante);

        Usuario adminGoogle = new Usuario();
        adminGoogle.setUsername("admin@tuempresa.com");
        adminGoogle.setPassword("");
        adminGoogle.setRole("ROLE_ADMIN");
        adminGoogle.setEmail("admin@tuempresa.com");
        usuarioRepository.save(adminGoogle);

        Usuario tecnicoGoogle = new Usuario();
        tecnicoGoogle.setUsername("tecnico@tuempresa.com");
        tecnicoGoogle.setPassword("");
        tecnicoGoogle.setRole("ROLE_TECNICO");
        tecnicoGoogle.setEmail("tecnico@tuempresa.com");
        usuarioRepository.save(tecnicoGoogle);
    }
}
