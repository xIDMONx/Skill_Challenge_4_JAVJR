package com.metaphorce.challenge.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain cadenaDeFiltros(HttpSecurity httpSecurity) throws Exception {
        // Tener una autorización básica por default
        httpSecurity.csrf(csrf -> csrf.disable()).httpBasic(Customizer.withDefaults())
                // Cualquier petición debe estar autenticada
                .authorizeHttpRequests(
                        (authz) ->
                                authz.requestMatchers("/authentication/**")// Cualquier URL que este dentro de "requestMatchers" es considerada "PÚBLICA"
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                );
        return httpSecurity.build();
    }

    @Bean
    public InMemoryUserDetailsManager crearUsuario() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("xIDMONx")
                .password("aH9Sqr")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(List.of(user));
    }
}
