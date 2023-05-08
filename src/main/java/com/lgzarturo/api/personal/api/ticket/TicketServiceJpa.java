package com.lgzarturo.api.personal.api.ticket;

import com.lgzarturo.api.personal.api.customer.Customer;
import com.lgzarturo.api.personal.api.customer.CustomerRepository;
import com.lgzarturo.api.personal.api.flight.Flight;
import com.lgzarturo.api.personal.api.flight.FlightRepository;
import com.lgzarturo.api.personal.api.ticket.dto.TicketRequest;
import com.lgzarturo.api.personal.api.ticket.dto.TicketResponse;
import com.lgzarturo.api.personal.api.ticket.mapper.TicketMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
            .price(flight.getPrice().multiply(BigDecimal.valueOf(1.25)))
            .purchaseDate(LocalDateTime.now())
            .arrivalDate(LocalDateTime.now())
            .departureDate(LocalDateTime.now())
            .build();
        flight.addTicket(ticket);
        customer.addTicket(ticket);
        Ticket savedTicket = ticketRepository.save(ticket);
        log.info("Ticket saved: {}", savedTicket);
        return TicketMapper.INSTANCE.mapToResponse(ticket);
    }

    @Override
    public TicketResponse read(Long id) {
        Ticket ticket = getById(id);
        return TicketMapper.INSTANCE.mapToResponse(ticket);
    }

    @Override
    public TicketResponse update(Long id, TicketRequest request) {
        Ticket ticket = getById(id);
        Flight flight = flightRepository.findById(request.getFlightId()).orElseThrow();
        ticket.setPrice(flight.getPrice().multiply(BigDecimal.valueOf(1.25)));
        ticket.setFlight(flight);
        ticket.setLastModifiedDate(LocalDateTime.now());
        Ticket savedTicket = ticketRepository.save(ticket);
        return TicketMapper.INSTANCE.mapToResponse(savedTicket);
    }

    @Override
    public void deleteById(Long id) {
        Ticket ticket = getById(id);
        ticketRepository.delete(ticket);
    }

    private Ticket getById(Long id) {
        return ticketRepository.findById(id).orElseThrow();
    }
}
