package com.lgzarturo.api.personal.api.customer;

import com.lgzarturo.api.personal.api.generic.Address;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name="customers_address")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CustomerAddress {
    @Id
    @Column(name = "customer_id")
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @Embedded
    private Address address;
}
