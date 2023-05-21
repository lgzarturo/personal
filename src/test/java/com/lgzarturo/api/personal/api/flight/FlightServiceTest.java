package com.lgzarturo.api.personal.api.flight;

import com.lgzarturo.api.personal.api.flight.dto.FlightRequest;
import com.lgzarturo.api.personal.api.flight.dto.FlightResponse;
import com.lgzarturo.api.personal.api.generic.SortType;
import com.lgzarturo.api.personal.utils.Helpers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.*;

@DataJpaTest
@ActiveProfiles("test")
class FlightServiceTest {

    @Autowired
    private FlightRepository flightRepository;
    private FlightService flightService;
    private List<Long> flightsId;
    private final Map<BigDecimal, Integer> prices = new HashMap<>();
    private final Map<Airline, Integer> airlines = new HashMap<>();
    private final Map<String, Integer> destinations = new HashMap<>();
    private final List<String> destinationItems = new ArrayList<>();

    @BeforeEach
    void setUp() {
        flightService = new FlightServiceJpa(flightRepository);
        flightRepository.deleteAll();
        List<Flight> flights = Helpers.getRandomFlights(10);
        flightRepository.saveAll(flights);
        flightsId = flights.stream().map(Flight::getId).toList();
        flights.forEach(flight -> {
            prices.put(flight.getPrice(), prices.getOrDefault(flight.getPrice(), 0) + 1);
            airlines.put(flight.getAirline(), airlines.getOrDefault(flight.getAirline(), 0) + 1);
            String key = flight.getOriginName() + "-" + flight.getDestinationName();
            destinationItems.add(key);
            destinations.put(key, destinations.getOrDefault(key, 0) + 1);
        });
    }

    @Test
    void itShouldGetAll() {
        // Given
        var flights = flightService.getAll(0,10, SortType.NONE);
        // When
        // Then
        flights.get().forEach(flight -> Assertions.assertTrue(flightsId.contains(flight.getId())));
    }

    @Test
    void itShouldGet() {
        // Given
        Random random = new Random();
        var flightId = flightsId.get(random.nextInt(flightsId.size()));
        // When
        var flight = flightService.get(flightId);
        // Then
        Assertions.assertEquals(flightId, flight.getId());
    }

    @Test
    void itShouldCreate() {
        // Given
        Random random = new Random();
        var flightRequest = new FlightRequest();
        flightRequest.setFlightNumber("AM" + random.nextInt(1000));
        flightRequest.setOriginLatitude(random.nextDouble());
        flightRequest.setOriginLongitude(random.nextDouble());
        flightRequest.setDestinationLatitude(random.nextDouble());
        flightRequest.setDestinationLongitude(random.nextDouble());
        flightRequest.setPrice(BigDecimal.valueOf(random.nextDouble()));
        flightRequest.setOriginName("MEX");
        flightRequest.setDestinationName("CUN");
        flightRequest.setAirline(Airline.AEROMEXICO);
        // When
        var flight = flightService.create(flightRequest);
        // Then
        Assertions.assertNotNull(flight.getId());
    }

    @Test
    void itShouldActive() {
        // Given
        Random random = new Random();
        var flightId = flightsId.get(random.nextInt(flightsId.size()));
        flightService.active(flightId);
        // When
        var flight = flightRepository.findById(flightId).orElseThrow();
        // Then
        Assertions.assertTrue(flight.getIsActive());
    }

    @Test
    void itShouldInactive() {
        // Given
        Random random = new Random();
        var flightId = flightsId.get(random.nextInt(flightsId.size()));
        flightService.inactive(flightId);
        // When
        var flight = flightRepository.findById(flightId).orElseThrow();
        // Then
        Assertions.assertFalse(flight.getIsActive());
    }

    @Test
    void itShouldGetFlightsByOriginAndDestination() {
        // Given
        Set<FlightResponse> flights = flightService.getFlightsByOriginAndDestination("MEX", "CUN");
        // When
        // Then
    }

    @Test
    void itShouldGetFlightsByPriceLessThan() {
        // Given
        Set<FlightResponse> flights = flightService.getFlightsByPriceLessThan(BigDecimal.valueOf(1000.0));
        // When
        // Then
    }

    @Test
    void itShouldGetFlightsBetweenPriceRange() {
        // Given
        Set<FlightResponse> flights = flightService.getFlightsBetweenPriceRange(BigDecimal.valueOf(1000.0), BigDecimal.valueOf(2000.0));
        // When
        // Then
    }

    @Test
    void itShouldGetFlightsByAirline() {
        // Given
        Set<FlightResponse> flights = flightService.getFlightsByAirline(Airline.AEROMEXICO);
        // When
        // Then
    }
}
