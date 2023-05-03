package com.lgzarturo.api.personal.api.customer;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends CrudRepository<Customer, UUID> {
    Optional<Customer> findByFullName(String fullName);
    Optional<Customer> findByPhoneNumber(String phoneNumber);
}
