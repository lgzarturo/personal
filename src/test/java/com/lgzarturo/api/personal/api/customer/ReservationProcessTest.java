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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Objects;

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
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private ReservationRepository reservationRepository;


    private Customer customer;
    private Hotel hotel;
    private Flight flight;

    @BeforeEach
    void setUp() {
        customer = Helpers.getRandomCustomer();
        customerRepository.save(customer);
        hotel = Helpers.getRandomHotel();
        hotelRepository.save(hotel);
        flight = Helpers.getRandomFlight();
        flightRepository.save(flight);
    }

    @Test
    void itShouldReservationProcess() {
        // Given
        Assertions.assertNotNull(customer);
        // When
        Tour tour = Helpers.getTourWithCustomer(customer);
        Ticket ticket = Helpers.getTicketWithFlightAndCustomer(flight, customer);
        Reservation reservation = Helpers.getReservationWithHotelAndCustomer(hotel, customer);
        tour.addTicket(ticket);
        tour.addReservation(reservation);
        tourRepository.save(tour);
        // Then
        Assertions.assertNotNull(tour);
        Long id = tour.getId();
        assert id != null;
        Tour tourFound = tourRepository.findById(id).orElse(null);
        assert tourFound != null;
        Long reservationId = Objects.requireNonNull(tourFound.getReservations().stream().findFirst().orElse(null)).getId();
        Long ticketId = Objects.requireNonNull(tourFound.getTickets().stream().findFirst().orElse(null)).getId();
        assert reservationId != null;
        assert ticketId != null;
        tourRepository.deleteById(id);
        Assertions.assertFalse(tourRepository.findById(id).isPresent());
        Assertions.assertFalse(reservationRepository.findById(reservationId).isPresent());
        Assertions.assertFalse(ticketRepository.findById(ticketId).isPresent());
    }
}
