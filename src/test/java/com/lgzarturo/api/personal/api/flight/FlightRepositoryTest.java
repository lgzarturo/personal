package com.lgzarturo.api.personal.api.flight;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
class FlightRepositoryTest {

    @Autowired
    private FlightRepository flightRepository;
    List<String> flightNumbers = new ArrayList<>();
    List<BigDecimal> prices = List.of(
        BigDecimal.valueOf(1000.0), BigDecimal.valueOf(2000.0), BigDecimal.valueOf(3000.0), BigDecimal.valueOf(4000.0),
        BigDecimal.valueOf(5000.0), BigDecimal.valueOf(6000.0), BigDecimal.valueOf(7000.0), BigDecimal.valueOf(8000.0),
        BigDecimal.valueOf(9000.0), BigDecimal.valueOf(10000.0)
    );
    List<String> destinations = List.of("MEX", "CUN", "MTY", "TIJ", "GDL", "CJS", "HMO", "CUL", "VER", "OAX");
    HashMap<BigDecimal, Integer> priceMap = new HashMap<>();
    HashMap<Airline, Integer> airlineMap = new HashMap<>();
    HashMap<String, Integer> destinationMap = new HashMap<>();

    @BeforeEach
    void setUp() {
        flightRepository.deleteAll();
        for (int i=0; i<10; i++) {
            Random random = new Random();
            Faker faker = new Faker();
            Flight flight = Flight.builder()
                .flightNumber(faker.code().gtin13())
                .airline(Airline.values()[i % Airline.values().length])
                .destinationName(destinations.get(random.nextInt(destinations.size())))
                .originName(destinations.get(random.nextInt(destinations.size())))
                .price(prices.get(random.nextInt(prices.size())))
                .build();
            flightRepository.save(flight);
            flightNumbers.add(flight.getFlightNumber());
            if (priceMap.containsKey(flight.getPrice())) {
                priceMap.put(flight.getPrice(), priceMap.get(flight.getPrice()) + 1);
            } else {
                priceMap.put(flight.getPrice(), 1);
            }
            if (airlineMap.containsKey(flight.getAirline())) {
                airlineMap.put(flight.getAirline(), airlineMap.get(flight.getAirline()) + 1);
            } else {
                airlineMap.put(flight.getAirline(), 1);
            }
            String key = flight.getOriginName() + "-" + flight.getDestinationName();
            if (destinationMap.containsKey(key)) {
                destinationMap.put(key, destinationMap.get(key) + 1);
            } else {
                destinationMap.put(key, 1);
            }
        }
    }

    @Test
    void itShouldSelectByFlightNumber() {
        // Given
        Random random = new Random();
        String flightNumber = flightNumbers.get(random.nextInt(flightNumbers.size()));
        // When
        Flight flight = flightRepository.selectByFlightNumber(flightNumber).orElse(null);
        // Then
        assertNotNull(flight);
    }

    @Test
    void itShouldSelectByPriceGreaterThan() {
        // Given
        Random random = new Random();
        BigDecimal price = prices.get(random.nextInt(prices.size()));
        Integer priceCount = 0;
        for (BigDecimal key : priceMap.keySet()) {
            if (key.compareTo(price) > 0) {
                priceCount += priceMap.get(key);
            }
        }
        // When
        Set<Flight> flights = flightRepository.selectByPriceGreaterThan(price);
        // Then
        assertEquals(priceCount, flights.size());
    }

    @Test
    void itShouldSelectByPriceLessThan() {
        // Given
        Random random = new Random();
        BigDecimal price = priceMap.keySet().stream().skip(random.nextInt(priceMap.size()))
            .findFirst().orElse(null);
        assert price != null;
        Integer priceCount = 0;
        for (BigDecimal key : priceMap.keySet()) {
            if (key.compareTo(price) < 0) {
                priceCount += priceMap.get(key);
            }
        }
        // When
        Set<Flight> flights = flightRepository.selectByPriceLessThan(price);
        // Then
        assertEquals(priceCount, flights.size());
    }

    @Test
    void itShouldSelectByPriceBetween() {
        // Given
        Random random = new Random();
        BigDecimal minPrice = priceMap.keySet().stream().skip(random.nextInt(priceMap.size()))
            .findFirst().orElse(null);
        BigDecimal maxPrice = priceMap.keySet().stream().skip(random.nextInt(priceMap.size()))
            .findFirst().orElse(null);
        assert minPrice != null;
        assert maxPrice != null;
        if (minPrice.compareTo(maxPrice) > 0) {
            BigDecimal temp = minPrice;
            minPrice = maxPrice;
            maxPrice = temp;
        }
        Integer priceCount = 0;
        for (BigDecimal key : priceMap.keySet()) {
            if (key.compareTo(minPrice) >= 0 && key.compareTo(maxPrice) <= 0) {
                priceCount += priceMap.get(key);
            }
        }
        // When
        Set<Flight> flights = flightRepository.selectByPriceBetween(
            minPrice.subtract(BigDecimal.valueOf(0.1)), maxPrice.add(BigDecimal.valueOf(0.1))
        );
        // Then
        assertEquals(priceCount, flights.size());
    }

    @Test
    void itShouldSelectByAirline() {
        // Given
        Random random = new Random();
        Airline airline = airlineMap.keySet().stream().skip(random.nextInt(airlineMap.size()))
            .findFirst().orElse(null);
        assert airline != null;
        // When
        Set<Flight> flights = flightRepository.selectByAirline(airline);
        // Then
        assertEquals(airlineMap.get(airline), flights.size());
    }

    @Test
    void itShouldSelectByOriginNameAndDestinationName() {
        // Given
        Random random = new Random();
        String key = destinationMap.keySet().stream().skip(random.nextInt(destinationMap.size()))
            .findFirst().orElse(null);
        assert key != null;
        String originName = key.split("-")[0];
        String destinationName = key.split("-")[1];
        // When
        Set<Flight> flights = flightRepository.selectByOriginNameAndDestinationName(originName, destinationName);
        // Then
        assertEquals(destinationMap.get(key), flights.size());
    }
}
