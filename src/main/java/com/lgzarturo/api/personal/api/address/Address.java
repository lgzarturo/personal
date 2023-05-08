package com.lgzarturo.api.personal.api.address;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

@EqualsAndHashCode(callSuper = true)
@Entity(name="addresses")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Address extends AbstractPersistable<Long> {
    @Column(length = 160)
    private String street;
    @Column(length = 40)
    private String city;
    @Column(length = 40)
    private String state;
    @Column(length = 80)
    private String country;
    @Column(length = 12)
    private String zipCode;
    private Double latitude;
    private Double longitude;
}
