package com.lgzarturo.api.personal.api.tour;

import com.lgzarturo.api.personal.api.customer.Customer;
import com.lgzarturo.api.personal.api.reservation.Reservation;
import com.lgzarturo.api.personal.api.ticket.Ticket;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity(name="tours")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Integer totalPersons;
    private BigDecimal price;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Reservation> reservations = new HashSet<>();
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Ticket> tickets = new HashSet<>();
    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customer;

    public void addReservation(Reservation reservation) {
        if (Objects.isNull(reservations)) reservations = new HashSet<>();
        reservations.add(reservation);
        reservation.setTour(this);
    }

    public void addTicket(Ticket ticket) {
        if (Objects.isNull(tickets)) tickets = new HashSet<>();
        tickets.add(ticket);
        ticket.setTour(this);
    }
}
