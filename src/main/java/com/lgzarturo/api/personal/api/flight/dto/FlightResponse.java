package com.lgzarturo.api.personal.api.flight.dto;

import com.lgzarturo.api.personal.api.flight.Airline;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FlightResponse {
    private Long id;
    private String flightNumber;
    private Double originLatitude;
    private Double originLongitude;
    private Double destinationLatitude;
    private Double destinationLongitude;
    private BigDecimal price;
    private String originName;
    private String destinationName;
    private Airline airline;
}
