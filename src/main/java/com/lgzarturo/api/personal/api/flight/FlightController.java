package com.lgzarturo.api.personal.api.flight;

import com.lgzarturo.api.personal.api.flight.dto.FlightRequest;
import com.lgzarturo.api.personal.api.flight.dto.FlightResponse;
import com.lgzarturo.api.personal.api.generic.CatalogController;
import com.lgzarturo.api.personal.api.generic.SortType;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping("api/v1/flights")
@AllArgsConstructor
@Slf4j
public class FlightController implements CatalogController<FlightResponse, FlightRequest, Long> {

    private final FlightService flightService;

    @GetMapping
    @Override
    public ResponseEntity<Page<FlightResponse>> getAll(
        @RequestParam(required = false) Integer page,
        @RequestParam(required = false) Integer size,
        @RequestParam(required = false) SortType sortType
    ) {
        if (Objects.isNull(page) || page <= 0) page = 1;
        if (Objects.isNull(size) || size <= 0) size = 10;
        if (Objects.isNull(sortType)) sortType = SortType.NONE;
        var response = flightService.getAll(page, size, sortType);
        return response.isEmpty()
            ? ResponseEntity.noContent().build()
            : ResponseEntity.ok(response);
    }

    @GetMapping("/filter")
    public ResponseEntity<Set<FlightResponse>> filter(
        @RequestParam FilterType filterType,
        @RequestParam(required = false) String origin,
        @RequestParam(required = false) String destination,
        @RequestParam(required = false) Airline airline,
        @RequestParam(required = false) Integer minPrice,
        @RequestParam(required = false) Integer maxPrice
    ) {
        var response = switch (filterType) {
            case ORIGIN_DESTINATION ->
                flightService.getFlightsByOriginAndDestination(origin, destination);
            case AIRLINE ->
                flightService.getFlightsByAirline(airline);
            case PRICE_LESS ->
                flightService.getFlightsByPriceLessThan(BigDecimal.valueOf(minPrice));
            case BETWEEN_PRICE ->
                flightService.getFlightsBetweenPriceRange(BigDecimal.valueOf(minPrice), BigDecimal.valueOf(maxPrice));
        };
        return response.isEmpty()
            ? ResponseEntity.noContent().build()
            : ResponseEntity.ok(response);
    }

    @PostMapping
    @Override
    public ResponseEntity<FlightResponse> post(@RequestBody @Valid FlightRequest request) {
        return ResponseEntity.ok(flightService.create(request));
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<FlightResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(flightService.get(id));
    }

    @PatchMapping("/{id}/active")
    @Override
    public ResponseEntity<Void> active(@PathVariable Long id) {
        flightService.active(id);
        return ResponseEntity.accepted().build();
    }

    @PatchMapping("/{id}/inactive")
    @Override
    public ResponseEntity<Void> inactive(@PathVariable Long id) {
        flightService.inactive(id);
        return ResponseEntity.accepted().build();
    }
}
