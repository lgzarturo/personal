package com.lgzarturo.api.personal.api.employee;

import com.lgzarturo.api.personal.api.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractAuditable;

import java.math.BigDecimal;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity(name="employees")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Employee extends AbstractAuditable<User, Long> {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    private String email;
    private String position;
    private String department;
    private BigDecimal salary;
    private String status;
    private LocalDate startDate;
    private LocalDate endDate;
    private String notes;
}
