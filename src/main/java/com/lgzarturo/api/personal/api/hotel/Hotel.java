package com.lgzarturo.api.personal.api.hotel;

import com.lgzarturo.api.personal.api.address.Address;
import com.lgzarturo.api.personal.api.reservation.Reservation;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
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
    @NotBlank
    @Column(length = 80, nullable = false)
    private String name;
    @ManyToOne
    @JoinColumn(name="address_id")
    private Address address;
    private Integer rating;
    private BigDecimal minimumPrice;
    private BigDecimal maximumPrice;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Reservation> reservations;

    public void addReservation(Reservation reservation) {
        if (Objects.isNull(reservations)) reservations = new HashSet<>();
        reservations.add(reservation);
        reservation.setHotel(this);
    }
}
