package com.gruposolutia.gestion_proyectos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
		
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
        	.cors(cors -> {})
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                        "/",
                        "/login",
                        "/api/public/**"
                ).permitAll()
                // Solo ADMIN
                .requestMatchers("/api/admin/**")
                .hasRole("ADMIN")

                // ADMIN o TECNICO
                .requestMatchers("/api/tecnico/**")
                .hasAnyRole("ADMIN", "TECNICO")
                // Cualquier usuario autenticado
                .anyRequest()
                .authenticated()
            )
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
	@Bean
	public UserDetailsService userDetailsService(PasswordEncoder encoder) {

		//Creamos usuarios en memoria
	    UserDetails admin = User.builder()
	            .username("admin")
	            .password(encoder.encode("1234"))
	            .roles("ADMIN")
	            .build();

	    UserDetails tecnico = User.builder()
	            .username("tecnico")
	            .password(encoder.encode("1234"))
	            .roles("TECNICO")
	            .build();

	    UserDetails visitante = User.builder()
	            .username("visitante")
	            .password(encoder.encode("1234"))
	            .roles("VISITANTE")
	            .build();

	    return new InMemoryUserDetailsManager(
	            admin,
	            tecnico,
	            visitante
	    );
	}
}
