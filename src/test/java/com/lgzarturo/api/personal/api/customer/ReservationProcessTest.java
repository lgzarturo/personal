package com.lgzarturo.api.personal.api.customer;

import com.lgzarturo.api.personal.api.flight.Airline;
import com.lgzarturo.api.personal.api.flight.Flight;
import com.lgzarturo.api.personal.api.flight.FlightRepository;
import com.lgzarturo.api.personal.api.hotel.Hotel;
import com.lgzarturo.api.personal.api.hotel.HotelRepository;
import com.lgzarturo.api.personal.api.reservation.Reservation;
import com.lgzarturo.api.personal.api.ticket.Ticket;
import com.lgzarturo.api.personal.api.tour.Tour;
import com.lgzarturo.api.personal.api.tour.TourRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class ReservationProcessTest {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private FlightRepository flightRepository;
    @Autowired
    private TourRepository tourRepository;

    private Customer customer;
    private Hotel hotel;
    private Flight flight;

    @BeforeEach
    void setUp() {
        customer = Customer.builder()
            .fullName("Arturo López Gaviria")
            .creditCardNumber("1234567890123456")
            .creditCardExpirationDate("12/24")
            .phoneNumber("1234567890")
            .build();
        customerRepository.save(customer);
        hotel = Hotel.builder()
            .name("Hotel 1")
            .rating(5)
            .minimumPrice(BigDecimal.valueOf(1000.0))
            .maximumPrice(BigDecimal.valueOf(2000.0))
            .build();
        hotelRepository.save(hotel);
        flight = Flight.builder()
            .flightNumber("1234567890")
            .price(BigDecimal.valueOf(1000.0))
            .originName("Origin 1")
            .destinationName("Destination 1")
            .airline(Airline.AEROMEXICO)
            .build();
        flightRepository.save(flight);
    }

    @Test
    void itShouldReservationProcess() {
        // Given
        Assertions.assertNotNull(customer);
        // When
        Tour tour = Tour.builder()
            .name("Tour 1")
            .description("Description 1")
            .price(BigDecimal.valueOf(1000.0))
            .customer(customer)
            .build();
        Ticket ticket = Ticket.builder()
            .arrivalDate(LocalDate.now())
            .departureDate(LocalDate.now().plusDays(3))
            .purchaseDate(LocalDate.now().minusDays(2))
            .price(tour.getPrice())
            .flight(flight)
            .tour(tour)
            .customer(customer)
            .build();
        Reservation reservation = Reservation.builder()
            .dateReservation(LocalDateTime.now())
            .dateCheckIn(LocalDate.now().plusDays(3))
            .dateCheckOut(LocalDate.now().minusDays(2))
            .totalNights(3)
            .totalAmount(tour.getPrice())
            .hotel(hotel)
            .tour(tour)
            .customer(customer)
            .build();
        tour.addTicket(ticket);
        tour.addReservation(reservation);
        tourRepository.save(tour);
        // Then
        System.out.println("Reservation: " + reservation);
    }
}
