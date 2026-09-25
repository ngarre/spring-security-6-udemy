package com.cursospringsecurity.app_security.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // Para que Spring cargue esta configuración al arrancar
@EnableMethodSecurity
public class SecurityConfig {

    @Bean // Registra esta configuración de seguridad como objeto de Spring
    //SecurityFilterChaines la cadena de filtros que Spring
    // Security utiliza para controlar las peticiones HTTP
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                auth -> auth
                        .requestMatchers("/loans").hasAuthority("VIEW_LOANS")
                        .requestMatchers("/balance").hasAuthority("VIEW_BALANCE")
                        .requestMatchers("/cards").hasAuthority("VIEW_CARDS")
                        // .requestMatchers("/account").hasAnyAuthority("VIEW_ACCOUNT", "VIEW_CARDS")
                        .anyRequest().permitAll())
                .formLogin(Customizer.withDefaults()) // Permite autenticarse mediante un formulario de login
                .httpBasic(Customizer.withDefaults()); // Para herraminetas como Postman/curl, usando HTTP Basic
        return http.build(); // Construye la cadena de seguridad con toda la configuración
    }



// Resumen: Esta clase es básicamente el lugar donde empiezas a decirle a Spring Security
// qué quieres proteger y cómo quieres autenticar a los usuarios.
// A partir de aquí, lo interesante es que authorizeHttpRequests() puede hacerse mucho más específico:
// permitAll(), hasRole(), hasAuthority(), requestMatchers(), etc.

    // Hardcodeando usuarios:
//    @Bean
//    InMemoryUserDetailsManager inMemoryUserDetailsManager(){
//        var admin = User.withUsername("admin")
//                .password("to_be_encoded")
//                .authorities("ADMIN")
//                .build();
//
//        var user = User.withUsername("user")
//                .password("to_be_encoded")
//                .authorities("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(admin, user);
//    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

}