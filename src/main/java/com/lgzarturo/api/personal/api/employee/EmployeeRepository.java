package com.lgzarturo.api.personal.api.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);
    @Query("SELECT e FROM employees e WHERE e.firstName = ?1 AND e.lastName = ?2")
    Employee getByFirstNameAndLastName(String firstName, String lastName);
    @Query("SELECT e FROM employees e WHERE e.email LIKE %?1")
    List<Employee> getAllByEmailDomain(String domain);
    @Query("SELECT e FROM employees e WHERE LOWER(e.firstName) LIKE LOWER(concat('%', ?1,'%'))")
    List<Employee> getAllByFirstNameLikeIgnoreCase(String firstName);
    @Query("SELECT COUNT (e) FROM employees e WHERE  e.id = ?1")
    int exists(Long employeeId);
}
