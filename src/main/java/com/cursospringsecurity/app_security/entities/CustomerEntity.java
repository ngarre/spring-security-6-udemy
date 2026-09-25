package com.cursospringsecurity.app_security.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Fetch;

import java.math.BigInteger;
import java.util.List;

@Entity
@Table(name="customers")
@Data
public class CustomerEntity {
    @Id
    private BigInteger id;
    private String email;
    private String password;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn
    private List<RoleEntity> roles;
}
