package com.lgzarturo.api.personal.api.ticket;

import com.lgzarturo.api.personal.api.customer.Customer;
import com.lgzarturo.api.personal.api.customer.CustomerRepository;
import com.lgzarturo.api.personal.api.flight.Flight;
import com.lgzarturo.api.personal.api.flight.FlightRepository;
import com.lgzarturo.api.personal.api.ticket.dto.TicketRequest;
import com.lgzarturo.api.personal.api.ticket.dto.TicketResponse;
import com.lgzarturo.api.personal.api.ticket.mapper.TicketMapper;
import com.lgzarturo.api.personal.utils.Helpers;
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

    private static final BigDecimal charge_price_percentage = BigDecimal.valueOf(1.25);

    private final CustomerRepository customerRepository;
    private final FlightRepository flightRepository;
    private final TicketRepository ticketRepository;

    @Override
    public TicketResponse create(TicketRequest request) {
        if (request.getCustomerId() == null || request.getFlightId() == null) {
            throw new IllegalArgumentException("Customer and Flight are required");
        }
        Flight flight = flightRepository.findById(request.getFlightId()).orElseThrow();
        Customer customer = customerRepository.findById(request.getCustomerId()).orElseThrow();
        Ticket ticket = Ticket.builder()
            .flight(flight)
            .customer(customer)
            .price(calculatePrice(flight.getPrice()))
            .purchaseDate(LocalDateTime.now())
            .arrivalDate(Helpers.getRandomDateSoon())
            .departureDate(Helpers.getRandomDateLater())
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
        if (request.getFlightId() == null) {
            throw new IllegalArgumentException("Flight are required");
        }
        Ticket ticket = getById(id);
        Flight flight = flightRepository.findById(request.getFlightId()).orElseThrow();
        ticket.setPrice(calculatePrice(flight.getPrice()));
        ticket.setFlight(flight);
        ticket.setArrivalDate(Helpers.getRandomDateSoon());
        ticket.setDepartureDate(Helpers.getRandomDateLater());
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

    @Override
    public BigDecimal findPrice(Long flightId) {
        Flight flight = flightRepository.findById(flightId).orElseThrow();
        return calculatePrice(flight.getPrice());
    }

    private BigDecimal calculatePrice(BigDecimal price) {
        return price.multiply(charge_price_percentage);
    }
}
