package com.lgzarturo.api.personal.api.customer;

import com.lgzarturo.api.personal.api.reservation.Reservation;
import com.lgzarturo.api.personal.api.ticket.Ticket;
import com.lgzarturo.api.personal.api.tour.Tour;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity(name="customers")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Customer {
    @Id
    private String id;
    @Column(length = 50)
    private String fullName;
    @Column(length = 20)
    private String creditCardNumber;
    @Column(length = 12)
    private String phoneNumber;
    private Integer totalReservations;
    private Integer totalFlights;
    private Integer totalLodgings;
    private Integer totalTickets;
    private Integer totalTours;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Ticket> tickets;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Reservation> reservations;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Tour> tours;
}
