package com.lgzarturo.api.personal.api.hotel;

import com.lgzarturo.api.personal.api.address.Address;
import com.lgzarturo.api.personal.api.address.AddressRepository;
import com.lgzarturo.api.personal.api.reservation.Reservation;
import com.lgzarturo.api.personal.api.reservation.ReservationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
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

    List<BigDecimal> prices = List.of(
        BigDecimal.valueOf(1000.0), BigDecimal.valueOf(2000.0), BigDecimal.valueOf(3000.0), BigDecimal.valueOf(4000.0),
        BigDecimal.valueOf(5000.0), BigDecimal.valueOf(6000.0), BigDecimal.valueOf(7000.0), BigDecimal.valueOf(8000.0),
        BigDecimal.valueOf(9000.0), BigDecimal.valueOf(10000.0)
    );
    List<BigDecimal> minimumPrices = new ArrayList<>();
    List<BigDecimal> maximumPrices = new ArrayList<>();
    HashMap<BigDecimal, BigDecimal> priceMap = new HashMap<>();
    List<String> countries = List.of(
        "Argentina", "Bolivia", "Brasil", "Chile", "Colombia", "Ecuador", "Guyana", "Paraguay", "Peru", "Surinam",
        "Uruguay", "Venezuela", "México", "Estados Unidos", "Canadá", "Cuba", "Haití", "República Dominicana"
    );
    List<String> selectedCountries = new ArrayList<>();
    List<Long> reservations = new ArrayList<>();

    @BeforeEach
    void setUp() {
        hotelRepository.deleteAll();
        for (int i=0; i<50; i++) {
            Random random = new Random();
            Hotel hotel = new Hotel();
            hotel.setName("Hotel " + i);
            BigDecimal minimumPrice = prices.get(random.nextInt(prices.size()));
            hotel.setMinimumPrice(minimumPrice);
            hotel.setRating(random.nextInt(5) + 1);
            hotel.setMaximumPrice(minimumPrice.add(BigDecimal.valueOf(1000.0)));
            BigDecimal savedMinimumPrice = hotel.getMinimumPrice().subtract(BigDecimal.valueOf(0.1));
            BigDecimal savedMaximumPrice = hotel.getMaximumPrice().add(BigDecimal.valueOf(0.1));
            Address address = new Address();
            address.setCountry(countries.get(random.nextInt(countries.size())));
            address.setCity("City " + i);
            address.setStreet("Street " + i);
            address.setZipCode("Zip Code " + i);
            addressRepository.save(address);
            hotel.setAddress(address);
            if (!selectedCountries.contains(address.getCountry())) {
                selectedCountries.add(address.getCountry());
            }
            minimumPrices.add(savedMinimumPrice);
            maximumPrices.add(savedMaximumPrice);
            priceMap.put(savedMinimumPrice, savedMaximumPrice);
            hotelRepository.save(hotel);
            int numberOfReservations = random.nextInt(5);
            for (int j=0; j<numberOfReservations; j++) {
                Reservation reservation = new Reservation();
                reservation.setHotel(hotel);
                reservation.setDateCheckIn(LocalDate.now());
                reservation.setDateCheckOut(LocalDate.now().plusDays(random.nextInt(10)));
                reservation.setTotalNights(reservation.getDateCheckOut().getDayOfYear() - reservation.getDateCheckIn().getDayOfYear());
                reservation.setTotalAmount(hotel.getMinimumPrice().multiply(BigDecimal.valueOf(reservation.getTotalNights())));
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
        Assertions.assertTrue(hotel.getReservations().stream().anyMatch(reservation1 -> reservation1.getId().equals(reservationId)));
    }
}