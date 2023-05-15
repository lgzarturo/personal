package com.lgzarturo.api.personal.api.hotel;

import com.lgzarturo.api.personal.api.reservation.Reservation;
import com.lgzarturo.api.personal.api.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractAuditable;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity(name="hotels")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Hotel extends AbstractAuditable<User, Long> {
    @NotBlank
    @Column(length = 80, nullable = false)
    private String name;
    private Integer rating;
    private BigDecimal minimumPrice;
    private BigDecimal maximumPrice;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(mappedBy = "hotel", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private HotelAddress hotelAddress;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Reservation> reservations;
    private Boolean isActive;

    public void addReservation(Reservation reservation) {
        if (Objects.isNull(reservations)) reservations = new HashSet<>();
        reservations.add(reservation);
        reservation.setHotel(this);
    }

    public void addHotelAddress(HotelAddress hotelAddress) {
        this.hotelAddress = hotelAddress;
        hotelAddress.setHotel(this);
    }
}
