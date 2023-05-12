package com.lgzarturo.api.personal.api.ticket;

import com.lgzarturo.api.personal.api.customer.CustomerRepository;
import com.lgzarturo.api.personal.api.flight.Airline;
import com.lgzarturo.api.personal.api.flight.Flight;
import com.lgzarturo.api.personal.api.flight.FlightRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class TicketServiceTest {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private FlightRepository flightRepository;
    private TicketService ticketService;
    private Long flightId;
    private final BigDecimal priceFlight = BigDecimal.valueOf(1000.0);
    private final BigDecimal totalPriceFlight = priceFlight.multiply(BigDecimal.valueOf(1.25));

    @BeforeEach
    void setUp() {
        ticketService = new TicketServiceJpa(customerRepository, flightRepository, ticketRepository);
        ticketRepository.deleteAll();
        flightRepository.deleteAll();
        Ticket ticket = Ticket.builder()
            .arrivalDate(LocalDateTime.now())
            .departureDate(LocalDateTime.now().plusDays(1))
            .purchaseDate(LocalDateTime.now())
            .price(BigDecimal.valueOf(1000.0))
            .build();
        Flight flight = Flight.builder()
            .flightNumber("123")
            .originName("origin")
            .destinationName("destination")
            .airline(Airline.AMERICAN_AIRLINES)
            .price(priceFlight)
            .build();
        flight.addTicket(ticket);
        flightRepository.save(flight);
        flightId = flight.getId();
    }

    @Test
    void itShouldFindPrice() {
        // Given
        BigDecimal total = ticketService.findPrice(flightId);
        // When
        // Then
        assertEquals(0, total.compareTo(totalPriceFlight));
    }
}
