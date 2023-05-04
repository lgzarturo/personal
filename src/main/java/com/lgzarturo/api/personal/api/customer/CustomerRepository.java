package com.lgzarturo.api.personal.api.customer;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
    Optional<Customer> findByFullName(String fullName);
    Optional<Customer> findByPhoneNumber(String phoneNumber);
}
