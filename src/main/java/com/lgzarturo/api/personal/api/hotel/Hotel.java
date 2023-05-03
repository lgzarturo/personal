package com.lgzarturo.api.personal.api.hotel;

import com.lgzarturo.api.personal.api.address.Address;
import com.lgzarturo.api.personal.api.reservation.Reservation;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity(name="hotels")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 80)
    private String name;
    @ManyToOne
    @JoinColumn(name="address_id")
    private Address address;
    private Integer rating;
    private BigDecimal minimumPrice;
    private BigDecimal maximumPrice;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Reservation> reservations = new HashSet<>();
}
