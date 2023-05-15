package com.lgzarturo.api.personal.api.flight.dto;

import com.lgzarturo.api.personal.api.flight.Airline;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FlightRequest {
    @NotNull
    private String flightNumber;
    private Double originLatitude;
    private Double originLongitude;
    private Double destinationLatitude;
    private Double destinationLongitude;
    @NotNull
    private BigDecimal price;
    @NotNull
    private String originName;
    @NotNull
    private String destinationName;
    @NotNull
    private Airline airline;
}
