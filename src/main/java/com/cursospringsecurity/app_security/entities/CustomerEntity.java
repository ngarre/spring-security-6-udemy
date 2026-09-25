package com.cursospringsecurity.app_security.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigInteger;

@Entity
@Table(name="customers")
@Data
public class CustomerEntity {
    @Id
    private BigInteger id;
    private String email;
    private String password;
    private String role;
}
