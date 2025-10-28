package com.lgzarturo.api.personal.api.customer;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Optional;

@RepositoryRestResource(path = "customers")
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    @RestResource(path = "by-full-name")
    Optional<Customer> findByFullName(String fullName);
    @RestResource(path = "by-phone-number")
    Optional<Customer> findByPhoneNumber(String phoneNumber);
    @Query("SELECT c FROM customers c WHERE c.phoneNumber  = :phone_number")
    Optional<Customer> getCustomerByPhoneNumber(@Param("phone_number") String phoneNumber);
}
