package com.lgzarturo.api.personal.api.reservation.dto;

import com.lgzarturo.api.personal.api.hotel.dto.HotelResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ReservationResponse {
    private Long id;
    private LocalDateTime dateReservation;
    private LocalDate dateCheckIn;
    private LocalDate dateCheckOut;
    private Integer totalPersons;
    private Integer totalNights;
    private BigDecimal totalAmount;
    private HotelResponse hotelResponse;
}
