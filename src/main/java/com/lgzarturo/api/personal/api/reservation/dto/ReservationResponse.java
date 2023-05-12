package com.lgzarturo.api.personal.api.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dateReservation;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateCheckIn;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateCheckOut;
    private Integer totalPersons;
    private Integer totalNights;
    private BigDecimal totalAmount;
    private HotelResponse hotelResponse;
}
