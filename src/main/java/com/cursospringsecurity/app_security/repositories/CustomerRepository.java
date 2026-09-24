package com.cursospringsecurity.app_security.repositories;

import com.cursospringsecurity.app_security.entities.CustomerEntity;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;

public interface CustomerRepository extends CrudRepository<CustomerEntity, BigInteger> {
}
