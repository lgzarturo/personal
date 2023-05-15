package com.lgzarturo.api.personal.api.flight;

import com.lgzarturo.api.personal.api.flight.mapper.FlightMapper;
import com.lgzarturo.api.personal.api.flight.dto.FlightRequest;
import com.lgzarturo.api.personal.api.flight.dto.FlightResponse;
import com.lgzarturo.api.personal.api.generic.SortType;
import com.lgzarturo.api.personal.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Slf4j
public class FlightServiceJpa implements FlightService {

    private final FlightRepository flightRepository;

    @Override
    public Page<FlightResponse> getAll(Integer page, Integer size, SortType sortType) {
        PageRequest pageRequest = switch (sortType) {
            case NONE -> PageRequest.of(page, size);
            case ASC -> PageRequest.of(page, size, Sort.by(FIELD_TO_SORT_BY).ascending());
            case DESC -> PageRequest.of(page, size, Sort.by(FIELD_TO_SORT_BY).descending());
        };
        return flightRepository.findAll(pageRequest).map(FlightMapper.INSTANCE::mapToResponse);
    }

    @Override
    public FlightResponse get(Long id) {
        return FlightMapper.INSTANCE.mapToResponse(getById(id));
    }

    @Transactional
    public FlightResponse create(FlightRequest request) {
        var flight = FlightMapper.INSTANCE.mapToEntity(request);
        flight.setIsActive(true);
        return FlightMapper.INSTANCE.mapToResponse(flightRepository.save(flight));
    }

    @Transactional
    @Override
    public void active(Long id) {
        getById(id);
        flightRepository.activate(id);
    }

    @Transactional
    @Override
    public void inactive(Long id) {
        getById(id);
        flightRepository.deactivate(id);
    }

    @Override
    public Set<FlightResponse> getFlightsByOriginAndDestination(String origin, String destination) {
        return flightRepository.selectByOriginNameAndDestinationName(origin, destination).stream()
                .map(FlightMapper.INSTANCE::mapToResponse)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<FlightResponse> getFlightsByPriceLessThan(BigDecimal price) {
        return flightRepository.selectByPriceLessThan(price).stream()
                .map(FlightMapper.INSTANCE::mapToResponse)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<FlightResponse> getFlightsBetweenPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return flightRepository.selectByPriceBetween(minPrice, maxPrice).stream()
                .map(FlightMapper.INSTANCE::mapToResponse)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<FlightResponse> getFlightsByAirline(Airline airline) {
        return flightRepository.selectByAirline(airline).stream()
                .map(FlightMapper.INSTANCE::mapToResponse)
                .collect(Collectors.toSet());
    }

    private Flight getById(Long id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found"));
    }
}
