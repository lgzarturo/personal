package com.lgzarturo.api.personal.api.ticket.dto;

import com.lgzarturo.api.personal.api.flight.dto.FlightResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TicketResponse {
    private Long id;
    private LocalDateTime arrivalDate;
    private LocalDateTime departureDate;
    private LocalDateTime purchaseDate;
    private BigDecimal price;
    private FlightResponse flight;
}
