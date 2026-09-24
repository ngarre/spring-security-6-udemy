package com.cursospringsecurity.app_security.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // Para que Spring cargue esta configuración al arrancar
public class SecurityConfig {

    @Bean // Registra esta configuración de seguridad como objeto de Spring
    //SecurityFilterChaines la cadena de filtros que Spring
    // Security utiliza para controlar las peticiones HTTP
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                auth -> auth.requestMatchers("/loans", "/balance", "/accounts", "/cards").authenticated()
                        .anyRequest().permitAll())
                .formLogin(Customizer.withDefaults()) // Permite autenticarse mediante un formulario de login
                .httpBasic(Customizer.withDefaults()); // Para herraminetas como Postman/curl, usando HTTP Basic
        return http.build(); // Construye la cadena de seguridad con toda la configuración
    }
}


// Resumen: Esta clase es básicamente el lugar donde empiezas a decirle a Spring Security
// qué quieres proteger y cómo quieres autenticar a los usuarios.
// A partir de aquí, lo interesante es que authorizeHttpRequests() puede hacerse mucho más específico:
// permitAll(), hasRole(), hasAuthority(), requestMatchers(), etc.