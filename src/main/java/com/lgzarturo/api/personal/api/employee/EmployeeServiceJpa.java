package com.lgzarturo.api.personal.api.employee;

import com.lgzarturo.api.personal.exceptions.ResourceNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceJpa implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceJpa(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Page<Employee> paginate(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    @Override
    public List<Employee> all() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee create(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee read(Long id) {
        return getById(id);
    }

    @Override
    public Employee update(Long id, Employee employee) {
        Employee employeeInstance = getById(id);
        BeanUtils.copyProperties(employee, employeeInstance, "id");
        return employeeRepository.save(employeeInstance);
    }

    @Override
    public void delete(Long id) {
        employeeRepository.delete(getById(id));
    }

    private Employee getById(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isEmpty()) {
            throw new ResourceNotFoundException("Employee not found");
        }
        return employee.get();
    }
}
