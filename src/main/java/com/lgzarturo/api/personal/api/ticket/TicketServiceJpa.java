package com.lgzarturo.api.personal.api.ticket;

import com.lgzarturo.api.personal.api.customer.Customer;
import com.lgzarturo.api.personal.api.customer.CustomerRepository;
import com.lgzarturo.api.personal.api.flight.Flight;
import com.lgzarturo.api.personal.api.flight.FlightRepository;
import com.lgzarturo.api.personal.api.flight.dto.FlightResponse;
import com.lgzarturo.api.personal.api.ticket.dto.TicketRequest;
import com.lgzarturo.api.personal.api.ticket.dto.TicketResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@Transactional
@Service
@Slf4j
public class TicketServiceJpa implements TicketService {

    private final CustomerRepository customerRepository;
    private final FlightRepository flightRepository;
    private final TicketRepository ticketRepository;

    @Override
    public TicketResponse create(TicketRequest request) {
        Flight flight = flightRepository.findById(request.getFlightId()).orElseThrow();
        Customer customer = customerRepository.findById(request.getCustomerId()).orElseThrow();
        Ticket ticket = Ticket.builder()
            .flight(flight)
            .customer(customer)
            .price(flight.getPrice().multiply(BigDecimal.valueOf(0.25)))
            .purchaseDate(LocalDateTime.now())
            .arrivalDate(LocalDateTime.now())
            .departureDate(LocalDateTime.now())
            .build();
        Ticket savedTicket = ticketRepository.save(ticket);
        log.info("Ticket saved: {}", savedTicket);
        return mapToResponse(savedTicket);
    }

    @Override
    public TicketResponse read(Long id) {
        return null;
    }

    @Override
    public TicketResponse update(Long aLong, TicketRequest request) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    private TicketResponse mapToResponse(Ticket ticket) {
        TicketResponse response = new TicketResponse();
        BeanUtils.copyProperties(ticket, response);
        FlightResponse flightResponse = new FlightResponse();
        BeanUtils.copyProperties(ticket.getFlight(), flightResponse);
        response.setFlight(flightResponse);
        return response;
    }
}
