package com.lgzarturo.api.personal.api.customer;

import com.lgzarturo.api.personal.api.flight.Flight;
import com.lgzarturo.api.personal.api.flight.FlightRepository;
import com.lgzarturo.api.personal.api.hotel.Hotel;
import com.lgzarturo.api.personal.api.hotel.HotelRepository;
import com.lgzarturo.api.personal.api.reservation.Reservation;
import com.lgzarturo.api.personal.api.reservation.ReservationRepository;
import com.lgzarturo.api.personal.api.ticket.Ticket;
import com.lgzarturo.api.personal.api.ticket.TicketRepository;
import com.lgzarturo.api.personal.api.tour.Tour;
import com.lgzarturo.api.personal.api.tour.TourRepository;
import com.lgzarturo.api.personal.utils.Helpers;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Objects;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private FlightRepository flightRepository;
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private TourRepository tourRepository;
    private Hotel hotelPersisted;
    private Tour tourPersisted;

    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();
        hotelRepository.deleteAll();
        tourRepository.deleteAll();
        Hotel hotel = Helpers.getRandomHotel();
        hotelPersisted = hotelRepository.save(hotel);
        Tour tour = Helpers.getRandomTour();
        tourPersisted = tourRepository.save(tour);
    }

    @Test
    void itShouldSaveCustomerWithRequiredFields() {
        // Given
        Customer customer = Helpers.getRandomCustomer();
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        // When
        Assertions.assertTrue(violations.isEmpty());
        customerRepository.save(customer);
        Long id = Objects.requireNonNull(customer.getId());
        Customer persistedCustomer = customerRepository.findById(id).orElseThrow();
        // Then
        assertNotNull(persistedCustomer.getId());
    }

    @Test
    void itShouldSaveCustomerWithAllFields() {
        // Given
        Ticket ticket = Helpers.getRandomTicket(tourPersisted);
        Flight flight = Helpers.getRandomFlight();
        Reservation reservation = Helpers.getRandomReservation(hotelPersisted);
        Customer customer = Helpers.getUserWithTicketReservationAndTour(ticket, reservation, tourPersisted);
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        // When
        Assertions.assertTrue(violations.isEmpty());

        // Relationships
        ticket.setCustomer(customer);
        ticket.setTour(tourPersisted);
        flight.addTicket(ticket);
        reservation.setCustomer(customer);
        reservation.setTour(tourPersisted);
        tourPersisted.setCustomer(customer);
        tourPersisted.addTicket(ticket);
        tourPersisted.addReservation(reservation);
        hotelPersisted.addReservation(reservation);

        // Save entities
        flightRepository.save(flight);
        reservationRepository.save(reservation);
        ticketRepository.save(ticket);
        hotelRepository.save(hotelPersisted);
        customerRepository.save(customer);

        Long id = Objects.requireNonNull(customer.getId());
        Customer persistedCustomer = customerRepository.findById(id).orElseThrow();
        // Then
        assertNotNull(persistedCustomer.getId());
        assertEquals(customer.getFullName(), persistedCustomer.getFullName());
        assertEquals(1, persistedCustomer.getReservations().size());
        assertEquals(1, persistedCustomer.getTickets().size());
        assertEquals(1, persistedCustomer.getTours().size());
    }

    @Test
    void itShouldGetCustomerByPhoneNumber() {
        // Given
        Customer customer = Helpers.getRandomCustomer();
        customerRepository.save(customer);
        // When
        Customer persistedCustomer = customerRepository.findByPhoneNumber(customer.getPhoneNumber()).orElseThrow();
        // Then
        assertEquals(customer.getPhoneNumber(), persistedCustomer.getPhoneNumber());
    }

    @Test
    void itShouldGetCustomerByFullName() {
        // Given
        Customer customer = Helpers.getRandomCustomer();
        customerRepository.save(customer);
        // When
        Customer persistedCustomer = customerRepository.findByFullName(customer.getFullName()).orElseThrow();
        // Then
        assertEquals(customer.getFullName(), persistedCustomer.getFullName());
    }
}
