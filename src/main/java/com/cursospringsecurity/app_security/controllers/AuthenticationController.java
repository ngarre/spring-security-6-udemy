package com.cursospringsecurity.app_security.controllers;

import com.cursospringsecurity.app_security.entities.JWTRequest;
import com.cursospringsecurity.app_security.entities.JWTResponse;
import com.cursospringsecurity.app_security.services.JWTService;
import com.cursospringsecurity.app_security.services.JWTUserDetailService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JWTUserDetailService jwtUserDetailService;
    private final JWTService jwtService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> postToken(@RequestBody JWTRequest request) {
        this.authenticate(request); // Antes de generar ningún token comprobamos que usuario y contraseña sean válidos

        final var userDetails = this.jwtUserDetailService.loadUserByUsername(request.getUsername());

        final var token = this.jwtService.generateToken(userDetails);
        return ResponseEntity.ok(new JWTResponse(token));
    }

    private void authenticate(JWTRequest request) {
        try {
            // Le paso objeto JAVA* a AuthenticationManager para que Spring Security compruebe si son credenciales válidas
            this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken( // Crea objeto JAVA con las credenciales que quiero comprobar*
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (BadCredentialsException | DisabledException e) { // Disabled exception se lanza cuando algún usuario está deshabilitado
            throw new RuntimeException(e.getMessage());
        }
    }
}
