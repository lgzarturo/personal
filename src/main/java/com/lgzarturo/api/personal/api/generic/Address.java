package com.lgzarturo.api.personal.api.generic;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Embeddable
public class Address {
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
