package com.lgzarturo.api.personal.api.hotel;

import com.lgzarturo.api.personal.api.address.Address;
import com.lgzarturo.api.personal.api.address.AddressRepository;
import com.lgzarturo.api.personal.api.reservation.Reservation;
import com.lgzarturo.api.personal.api.reservation.ReservationRepository;
import com.lgzarturo.api.personal.utils.Helpers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
class HotelRepositoryTest {

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private ReservationRepository reservationRepository;

    List<BigDecimal> minimumPrices = new ArrayList<>();
    List<BigDecimal> maximumPrices = new ArrayList<>();
    HashMap<BigDecimal, BigDecimal> priceMap = new HashMap<>();
    List<String> selectedCountries = new ArrayList<>();
    List<Long> reservations = new ArrayList<>();

    @BeforeEach
    void setUp() {
        hotelRepository.deleteAll();
        for (int i=0; i<50; i++) {
            Random random = new Random();
            Hotel hotel = Helpers.getRandomHotel();
            BigDecimal savedMinimumPrice = hotel.getMinimumPrice().subtract(BigDecimal.valueOf(0.1));
            BigDecimal savedMaximumPrice = hotel.getMaximumPrice().add(BigDecimal.valueOf(0.1));
            Address address = Helpers.getRandomAddress();
            addressRepository.save(address);
            hotel.setAddress(address);
            if (!selectedCountries.contains(address.getCountry())) {
                selectedCountries.add(address.getCountry());
            }
            minimumPrices.add(savedMinimumPrice);
            maximumPrices.add(savedMaximumPrice);
            priceMap.put(savedMinimumPrice, savedMaximumPrice);
            hotelRepository.save(hotel);
            int numberOfReservations = random.nextInt(5) + 1;
            for (int j=0; j<numberOfReservations; j++) {
                Reservation reservation = Helpers.getRandomReservation(hotel);
                hotel.addReservation(reservation);
                reservationRepository.save(reservation);
                reservations.add(reservation.getId());
            }
        }
    }

    @Test
    void itShouldFindByMaximumPriceLessThan() {
        // Given
        Random random = new Random();
        BigDecimal maximumPrice = maximumPrices.get(random.nextInt(maximumPrices.size()));
        // When
        Set<Hotel> hotels = hotelRepository.findAllByMaximumPriceLessThan(maximumPrice);
        // Then
        Assertions.assertTrue(hotels.stream().allMatch(hotel -> hotel.getMaximumPrice().compareTo(maximumPrice) < 0));
    }

    @Test
    void itShouldFindByMinimumPriceGreaterThanEqualAndMaximumPriceLessThanEqual() {
        // Given
        priceMap.forEach((minimumPrice, maximumPrice) -> {
            // When
            Set<Hotel> hotels = hotelRepository.findAllByMinimumPriceGreaterThanEqualAndMaximumPriceLessThanEqual(minimumPrice, maximumPrice);
            // Then
            Assertions.assertTrue(hotels.stream().allMatch(hotel -> hotel.getMinimumPrice().compareTo(minimumPrice) >= 0 && hotel.getMaximumPrice().compareTo(maximumPrice) <= 0));
        });
    }

    @Test
    void itShouldFindAllByAddress_Country() {
        // Given
        String country = selectedCountries.get(new Random().nextInt(selectedCountries.size()));
        // When
        Set<Hotel> hotels = hotelRepository.findAllByAddress_Country(country);
        // Then
        Assertions.assertTrue(hotels.stream().allMatch(hotel -> hotel.getAddress().getCountry().equals(country)));
    }

    @Test
    void itShouldFindAllByRating() {
        // Given
        Integer rating = new Random().nextInt(5) + 1;
        // When
        Set<Hotel> hotels = hotelRepository.findAllByRating(rating);
        // Then
        Assertions.assertTrue(hotels.stream().allMatch(hotel -> hotel.getRating().equals(rating)));
    }

    @Test
    void itShouldFindByReservations() {
        // Given
        Long reservationId = reservations.get(new Random().nextInt(reservations.size()));
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow();
        // When
        Hotel hotel = hotelRepository.findByReservationsIn(Set.of(reservation)).orElseThrow();
        // Then
        Assertions.assertTrue(hotel.getReservations().stream().anyMatch(reservation1 -> Objects.equals(reservation1.getId(), reservationId)));
    }
}
