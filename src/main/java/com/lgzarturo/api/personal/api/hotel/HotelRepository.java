package com.lgzarturo.api.personal.api.hotel;

import com.lgzarturo.api.personal.api.reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    Set<Hotel> findAllByMaximumPriceLessThan(BigDecimal maximumPrice);
    Set<Hotel> findAllByMinimumPriceGreaterThanEqualAndMaximumPriceLessThanEqual(BigDecimal minimumPrice, BigDecimal maximumPrice);
    Set<Hotel> findAllByRatingGreaterThanEqual(Integer rating);
    @Query("SELECT h FROM hotels h WHERE h.rating BETWEEN :rating AND :rating2")
    Set<Hotel> findAllByRatingBetween(Integer rating, Integer rating2);
    Set<Hotel> findAllByRating(Integer rating);
    @Query("SELECT h FROM hotels h WHERE h.hotelAddress.address.city = :city")
    Set<Hotel> findAllByCity(String city);
    @Query("SELECT h FROM hotels h WHERE h.hotelAddress.address.country = :country")
    Set<Hotel> findAllByCountry(String country);
    Optional<Hotel> findByReservationsIn(Set<Reservation> reservations);
}
