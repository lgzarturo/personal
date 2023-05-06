package com.lgzarturo.api.personal.api.customer;

import com.lgzarturo.api.personal.api.flight.Airline;
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
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

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
        Hotel hotel = new Hotel();
        hotel.setId(1L);
        hotel.setName("Hotel");
        hotel.setAddress(null);
        hotel.setRating(5);
        hotel.setMinimumPrice(BigDecimal.valueOf(1000));
        hotel.setMaximumPrice(BigDecimal.valueOf(10000));
        hotelPersisted = hotelRepository.save(hotel);
        Tour tour = new Tour();
        tour.setId(1L);
        tour.setName("Tour");
        tour.setDescription("Description");
        tour.setPrice(BigDecimal.valueOf(1000));
        tourPersisted = tourRepository.save(tour);
    }

    @Test
    void itShouldSaveCustomerWithRequiredFields() {
        // Given
        Customer customer = Customer.builder()
            .fullName("Arturo")
            .creditCardNumber("1234567890123456")
            .phoneNumber("1234567890")
            .build();
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
        Ticket ticket = new Ticket();
        ticket.setArrivalDate(LocalDate.now().plusDays(2));
        ticket.setDepartureDate(LocalDate.now().plusDays(10));
        ticket.setPurchaseDate(LocalDate.now());
        ticket.setPrice(BigDecimal.valueOf(1000));
        ticket.setTour(tourPersisted);

        Flight flight = new Flight();
        flight.setId(1L);
        flight.setAirline(Airline.AEROMEXICO);
        flight.setFlightNumber("123");
        flight.setDestinationName("MEX");
        flight.setOriginName("CUN");
        flight.setOriginLatitude(0.0);
        flight.setOriginLongitude(0.0);
        flight.setDestinationLatitude(0.0);
        flight.setDestinationLongitude(0.0);
        flight.setPrice(BigDecimal.valueOf(1000));

        Reservation reservation = new Reservation();
        reservation.setDateReservation(LocalDateTime.now());
        reservation.setDateCheckIn(LocalDate.now().plusDays(2));
        reservation.setDateCheckOut(LocalDate.now().plusDays(10));
        reservation.setTotalPersons(2);
        reservation.setTotalNights(8);
        reservation.setTotalAmount(BigDecimal.valueOf(3000));
        reservation.setHotel(hotelPersisted);

        Customer customer = Customer.builder()
            .fullName("Arturo")
            .creditCardNumber("1234567890123456")
            .phoneNumber("1234567890")
            .totalReservations(1)
            .totalFlights(20)
            .totalLodgings(15)
            .totalTickets(1)
            .totalTours(1)
            .tickets(Set.of(ticket))
            .reservations(Set.of(reservation))
            .tours(Set.of(tourPersisted))
            .build();

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
        Customer customer = Customer.builder()
            .fullName("John Doe")
            .creditCardNumber("1234567890123456")
            .phoneNumber("1234567890")
            .build();
        customerRepository.save(customer);
        // When
        Customer persistedCustomer = customerRepository.findByPhoneNumber(customer.getPhoneNumber()).orElseThrow();
        // Then
        assertEquals(customer.getPhoneNumber(), persistedCustomer.getPhoneNumber());
    }

    @Test
    void itShouldGetCustomerByFullName() {
        // Given
        Customer customer = Customer.builder()
            .fullName("John Doe")
            .creditCardNumber("1234567890123456")
            .phoneNumber("1234567890")
            .build();
        customerRepository.save(customer);
        // When
        Customer persistedCustomer = customerRepository.findByFullName(customer.getFullName()).orElseThrow();
        // Then
        assertEquals(customer.getFullName(), persistedCustomer.getFullName());
    }
}
