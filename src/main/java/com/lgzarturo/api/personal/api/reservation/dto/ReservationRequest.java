package com.lgzarturo.api.personal.api.reservation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ReservationRequest {
    @NotNull
    private Long clientId;
    @NotNull
    private Long hotelId;
    @NotNull
    @Range(min = 1, max = 20)
    private Integer totalNights;
    @NotNull
    @Range(min = 1, max = 10)
    private Integer totalPersons;
}
