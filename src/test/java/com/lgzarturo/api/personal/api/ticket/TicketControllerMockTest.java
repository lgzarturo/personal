package com.lgzarturo.api.personal.api.ticket;

import com.lgzarturo.api.personal.api.customer.Customer;
import com.lgzarturo.api.personal.api.flight.Flight;
import com.lgzarturo.api.personal.api.ticket.dto.TicketRequest;
import com.lgzarturo.api.personal.api.ticket.dto.TicketResponse;
import com.lgzarturo.api.personal.api.ticket.mapper.TicketMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TicketControllerMockTest {

    @InjectMocks
    TicketController ticketController;
    @Mock
    TicketService ticketService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void itShouldName() {
        // Given
        var request = new TicketRequest();
        request.setCustomerId(1L);
        request.setFlightId(1L);
        var price = BigDecimal.valueOf(1000);
        var flight = Flight.builder().flightNumber("11111").price(price).build();
        var customer = Customer.builder().fullName("arturo").build();
        Ticket ticket = Ticket.builder().price(BigDecimal.valueOf(1250.00)).flight(flight).customer(customer).build();
        TicketResponse responseTicket = TicketMapper.INSTANCE.mapToResponse(ticket);
        responseTicket.setId(1L);
        Mockito.when(ticketService.create(request)).thenReturn(responseTicket);
        // When
        var response = ticketController.post(request);
        // Then
        Assertions.assertNotNull(response);
        var priceExpectedInt = price.multiply(BigDecimal.valueOf(1.25)).intValue();
        var priceActualInt = Objects.requireNonNull(response.getBody()).getPrice().intValue();
        assertEquals(priceExpectedInt, priceActualInt);
        var responseLocation = Objects.requireNonNull(response.getHeaders().getLocation()).toString();
        Assertions.assertNotNull(responseLocation);
        assertEquals("/api/v1/tickets/1",  responseLocation);
    }
}
