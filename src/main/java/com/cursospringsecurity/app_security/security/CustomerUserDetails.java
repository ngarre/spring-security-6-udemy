package com.cursospringsecurity.app_security.security;

import com.cursospringsecurity.app_security.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class CustomerUserDetails implements UserDetailsService {

    private final CustomerRepository customerRepository;


    @Override
    // A este metodo lo llama internamente SpringSecurity
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.customerRepository.findByEmail(username)
                .map(customer -> {
                    var authorities = List.of(new SimpleGrantedAuthority(customer.getRole()));
                    return new User( // Importa el orden de los argumentos:
                            customer.getEmail(), // Esto será el username para Spring Security
                            customer.getPassword(), // Esto será la contraseña almacenada
                            authorities); // Esto serán los permisos roles
                }).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}

// Esta clase busca un usuario y lo devuelve convertido a UserDetails