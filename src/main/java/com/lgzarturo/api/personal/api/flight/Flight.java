package com.lgzarturo.api.personal.api.flight;

import com.lgzarturo.api.personal.api.ticket.Ticket;
import com.lgzarturo.api.personal.api.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractAuditable;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity(name="flights")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Flight extends AbstractAuditable<User, Long> {
    @Column(length = 40, unique = true)
    private String flightNumber;
    private Double originLatitude;
    private Double originLongitude;
    private Double destinationLatitude;
    private Double destinationLongitude;
    private BigDecimal price;
    @Column(length = 40)
    private String originName;
    @Column(length = 40)
    private String destinationName;
    @Column(length = 40)
    @Enumerated(EnumType.STRING)
    private Airline airline;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Ticket> tickets;
    private Boolean isActive;

    public void addTicket(Ticket ticket) {
        if (Objects.isNull(tickets)) tickets = new HashSet<>();
        tickets.add(ticket);
        ticket.setFlight(this);
    }
}
