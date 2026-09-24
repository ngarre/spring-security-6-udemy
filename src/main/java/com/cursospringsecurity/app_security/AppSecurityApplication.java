package com.cursospringsecurity.app_security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity // Realmente esto en esta versión de Spring ya no es necesario
public class AppSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppSecurityApplication.class, args);
	}

}
