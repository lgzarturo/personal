package com.lgzarturo.api.personal.api.ticket;

import com.lgzarturo.api.personal.api.customer.Customer;
import com.lgzarturo.api.personal.api.flight.Flight;
import com.lgzarturo.api.personal.api.tour.Tour;
import com.lgzarturo.api.personal.api.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractAuditable;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity(name="tickets")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Ticket extends AbstractAuditable<User, Long> {
    private LocalDate arrivalDate;
    private LocalDate departureDate;
    private LocalDate purchaseDate;
    private BigDecimal price;
    @ManyToOne
    @JoinColumn(name="flight_id")
    private Flight flight;
    @ManyToOne
    @JoinColumn(name="tour_id")
    private Tour tour;
    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customer;
}
