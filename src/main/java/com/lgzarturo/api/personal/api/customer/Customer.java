package com.lgzarturo.api.personal.api.customer;

import com.lgzarturo.api.personal.api.reservation.Reservation;
import com.lgzarturo.api.personal.api.ticket.Ticket;
import com.lgzarturo.api.personal.api.tour.Tour;
import com.lgzarturo.api.personal.api.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractAuditable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity(name="customers")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Customer extends AbstractAuditable<User, UUID> {
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
    private Set<Ticket> tickets = new HashSet<>();
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Reservation> reservations = new HashSet<>();
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Tour> tours = new HashSet<>();
}
