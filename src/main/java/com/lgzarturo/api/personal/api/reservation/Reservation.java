package com.lgzarturo.api.personal.api.reservation;

import com.lgzarturo.api.personal.api.customer.Customer;
import com.lgzarturo.api.personal.api.hotel.Hotel;
import com.lgzarturo.api.personal.api.tour.Tour;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name="reservations")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Reservation {
    @Id
    private UUID id;
    private LocalDateTime dateReservation;
    private LocalDate dateCheckIn;
    private LocalDate dateCheckOut;
    private Integer totalPersons;
    private Integer totalNights;
    private BigDecimal totalAmount;
    @ManyToOne
    @JoinColumn(name="hotel_id")
    private Hotel hotel;
    @ManyToOne
    @JoinColumn(name="tour_id", nullable = true)
    private Tour tour;
    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customer;
}
