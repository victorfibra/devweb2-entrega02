package com.example.entrega02.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desabilita CSRF para facilitar testes via Postman
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin())) // Essencial para o H2 Console
                .authorizeHttpRequests(authorize -> authorize
                        // Acessos Públicos
                        .requestMatchers(new AntPathRequestMatcher("/api/info")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
                        
                        // Permissões para Clientes, Planos e Serviços (Necessários para o Admin popular o banco)
                        .requestMatchers(new AntPathRequestMatcher("/api/clientes/**")).hasRole("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/api/planos/**")).hasRole("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/api/servicos/**")).hasRole("ADMIN")

                        // Configuração dos níveis de acesso para Contratos (Atividade do Mestrado)
                        .requestMatchers(new AntPathRequestMatcher("/api/contratos/**", "GET")).hasAnyRole("CLIENTE", "TECNICO", "ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/api/contratos/**", "PUT")).hasAnyRole("TECNICO", "ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/api/contratos/**", "POST")).hasRole("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/api/contratos/**", "DELETE")).hasRole("ADMIN")
                        
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults()); // Habilita o login via Basic Auth no Postman

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder.encode("admin"))
                .roles("ADMIN")
                .build();

        UserDetails tecnico = User.withUsername("tecnico")
                .password(passwordEncoder.encode("tecnico"))
                .roles("TECNICO")
                .build();

        UserDetails cliente = User.withUsername("cliente")
                .password(passwordEncoder.encode("cliente"))
                .roles("CLIENTE")
                .build();

        return new InMemoryUserDetailsManager(admin, tecnico, cliente);
    }
}