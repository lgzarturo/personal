package com.lgzarturo.api.personal.api.customer;

import com.lgzarturo.api.personal.api.reservation.Reservation;
import com.lgzarturo.api.personal.api.ticket.Ticket;
import com.lgzarturo.api.personal.api.tour.Tour;
import com.lgzarturo.api.personal.api.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.domain.AbstractAuditable;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity(name="customers")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Customer extends AbstractAuditable<User, Long> {
    @NotBlank
    @Length(min = 3, max = 50)
    @Column(length = 50, nullable = false)
    private String fullName;
    @NotBlank
    @Length(min = 12, max = 20)
    @Column(length = 20, nullable = false)
    private String creditCardNumber;
    @Length(min = 5, max = 7)
    @Column(length = 7)
    private String creditCardExpirationDate;
    @NotBlank
    @Length(min = 10, max = 12)
    @Column(length = 12, nullable = false)
    private String phoneNumber;
    private Integer totalReservations = 0;
    private Integer totalFlights = 0;
    private Integer totalLodgings = 0;
    private Integer totalTickets = 0;
    private Integer totalTours = 0;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Ticket> tickets;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Reservation> reservations;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Tour> tours;

    public void addTicket(Ticket ticket) {
        if (tickets == null) tickets = new HashSet<>();
        tickets.add(ticket);
        ticket.setCustomer(this);
    }

    public void addReservation(Reservation reservation) {
        if (reservations == null) reservations = new HashSet<>();
        reservations.add(reservation);
        reservation.setCustomer(this);
    }

    public void addTour(Tour tour) {
        if (tours == null) tours = new HashSet<>();
        tours.add(tour);
        tour.setCustomer(this);
    }
}
