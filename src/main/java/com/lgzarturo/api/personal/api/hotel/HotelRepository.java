package com.lgzarturo.api.personal.api.hotel;

import com.lgzarturo.api.personal.api.reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    Set<Hotel> findAllByMaximumPriceLessThan(BigDecimal maximumPrice);
    Set<Hotel> findAllByMinimumPriceGreaterThanEqualAndMaximumPriceLessThanEqual(BigDecimal minimumPrice, BigDecimal maximumPrice);
    Set<Hotel> findAllByAddress_Country(String country);
    Set<Hotel> findAllByRating(Integer rating);
    Optional<Hotel> findByReservationsIn(Set<Reservation> reservations);
}
