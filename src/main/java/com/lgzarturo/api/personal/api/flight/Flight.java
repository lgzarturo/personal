package com.lgzarturo.api.personal.api.flight;

import com.lgzarturo.api.personal.api.ticket.Ticket;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Entity(name="flights")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Ticket> tickets;
}
