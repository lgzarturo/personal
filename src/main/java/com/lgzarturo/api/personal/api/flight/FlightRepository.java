package com.lgzarturo.api.personal.api.flight;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    @Query("SELECT f FROM flights f WHERE f.flightNumber = :flightNumber")
    Optional<Flight> selectByFlightNumber(String flightNumber);
    @Query("SELECT f FROM flights f WHERE f.price > :price")
    Set<Flight> selectByPriceGreaterThan(BigDecimal price);
    @Query("SELECT f FROM flights f WHERE f.price < :price")
    Set<Flight> selectByPriceLessThan(BigDecimal price);
    @Query("SELECT f FROM flights f WHERE f.price BETWEEN :min AND :max")
    Set<Flight> selectByPriceBetween(BigDecimal min, BigDecimal max);
    @Query("SELECT f FROM flights f WHERE f.airline = :airline")
    Set<Flight> selectByAirline(Airline airline);
    @Query("SELECT f FROM flights f WHERE f.originName = :originName AND f.destinationName = :destinationName")
    Set<Flight> selectByOriginNameAndDestinationName(String originName, String destinationName);
    @Query("SELECT f FROM flights f JOIN FETCH f.tickets t WHERE f.id = :flightId")
    Optional<Flight> selectFlightAndTicketById(Long flightId);
}
