package com.lgzarturo.api.personal.api.flight;

import com.lgzarturo.api.personal.api.flight.dto.FlightRequest;
import com.lgzarturo.api.personal.api.flight.dto.FlightResponse;
import com.lgzarturo.api.personal.api.generic.CatalogService;

import java.math.BigDecimal;
import java.util.Set;

public interface FlightService extends CatalogService<FlightResponse, Long> {
    String FIELD_TO_SORT_BY = "price";
    FlightResponse create(FlightRequest request);
    Set<FlightResponse> getFlightsByOriginAndDestination(String origin, String destination);
    Set<FlightResponse> getFlightsByPriceLessThan(BigDecimal price);
    Set<FlightResponse> getFlightsBetweenPriceRange(BigDecimal minPrice, BigDecimal maxPrice);
    Set<FlightResponse> getFlightsByAirline(Airline airline);
}
