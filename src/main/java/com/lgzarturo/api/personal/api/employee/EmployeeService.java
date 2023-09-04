package com.lgzarturo.api.personal.api.employee;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeService {
    Page<Employee> paginate(Pageable pageable);
    List<Employee> all();
    Employee create(Employee employee);
    Employee read(Long id);
    Employee update(Long id, Employee employee);
    void delete(Long id);
}
