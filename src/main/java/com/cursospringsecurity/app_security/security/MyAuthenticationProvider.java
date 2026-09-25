package com.cursospringsecurity.app_security.security;

import com.cursospringsecurity.app_security.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class MyAuthenticationProvider implements AuthenticationProvider {

    private CustomerRepository customerRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) {

        // ¿Quién intenta iniciar sesión?
        final var username = authentication.getName();

        // ¿Qué contraseña ha escrito?
        final var pwd = authentication.getCredentials().toString();

        // Busca ese usuario en mi BBDD
        final var customerFromDb =
                customerRepository.findByEmail(username);

        // Si no existe, login incorrecto
        final var customer =
                customerFromDb.orElseThrow(
                        () -> new BadCredentialsException("Invalid credentials")
                );

        // Obtén su contraseña real
        final var customerPwd = customer.getPassword();

        // ¿La contraseña introducida coincide?
        if (passwordEncoder.matches(pwd, customerPwd)) {

            // Obtén los permisos de ese usuario
            final var roles = customer.getRoles();
            final var authorities = roles
                    .stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName()))
                    .collect(Collectors.toList());

            // Devuelve un Authentication YA AUTENTICADO
            return new UsernamePasswordAuthenticationToken(
                    username,
                    pwd,
                    authorities
            );

        } else {

            // Password incorrecta
            throw new BadCredentialsException("Invalid credentials");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
