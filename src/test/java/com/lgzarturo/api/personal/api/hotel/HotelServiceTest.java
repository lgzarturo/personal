package com.lgzarturo.api.personal.api.hotel;

import com.lgzarturo.api.personal.api.generic.SortType;
import com.lgzarturo.api.personal.api.hotel.dto.HotelResponse;
import com.lgzarturo.api.personal.utils.Helpers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

@DataJpaTest
@ActiveProfiles("test")
class HotelServiceTest {

    @Autowired
    private HotelRepository hotelRepository;
    private HotelService hotelService;
    private List<Long> hotelsId;
    private final Map<String, Integer> cities = new HashMap<>();
    private final Map<String, Integer> countries = new HashMap<>();
    private final Map<Integer, Integer> ratings = new HashMap<>();

    @BeforeEach
    void setUp() {
        hotelService = new HotelServiceJpa(hotelRepository);
        hotelRepository.deleteAll();
        List<Hotel> hotels = Helpers.getRandomHotels(10);
        hotelRepository.saveAll(hotels);
        hotelsId = hotels.stream().map(Hotel::getId).toList();
        hotels.forEach(hotel -> {
            cities.put(hotel.getHotelAddress().getAddress().getCity(), cities.getOrDefault(hotel.getHotelAddress().getAddress().getCity(), 0) + 1);
            countries.put(hotel.getHotelAddress().getAddress().getCountry(), countries.getOrDefault(hotel.getHotelAddress().getAddress().getCountry(), 0) + 1);
            ratings.put(hotel.getRating(), ratings.getOrDefault(hotel.getRating(), 0) + 1);
        });
    }

    @Test
    void itShouldGetAll() {
        // Given
        var hotels = hotelService.getAll(0,10, SortType.NONE);
        // When
        // Then
        hotels.get().forEach(hotel -> Assertions.assertTrue(hotelsId.contains(hotel.getId())));
    }

    @Test
    void itShouldGet() {
        // Given
        Random random = new Random();
        var hotelId = hotelsId.get(random.nextInt(hotelsId.size()));
        // When
        var hotel = hotelService.get(hotelId);
        // Then
        Assertions.assertEquals(hotelId, hotel.getId());
    }

    @Test
    void itShouldActive() {
        // Given
        Random random = new Random();
        var hotelId = hotelsId.get(random.nextInt(hotelsId.size()));
        hotelService.active(hotelId);
        // When
        var hotel = hotelRepository.findById(hotelId).orElseThrow();
        // Then
        Assertions.assertTrue(hotel.getIsActive());
    }

    @Test
    void itShouldInactive() {
        // Given
        Random random = new Random();
        var hotelId = hotelsId.get(random.nextInt(hotelsId.size()));
        hotelService.inactive(hotelId);
        // When
        var hotel = hotelRepository.findById(hotelId).orElseThrow();
        // Then
        Assertions.assertFalse(hotel.getIsActive());
    }

    @Test
    void itShouldGetHotelsByRatingGreaterThan() {
        // Given
        Set<HotelResponse> hotels = hotelService.getHotelsByRatingGreaterThan(4);
        // When
        var hotelRating = ratings.getOrDefault(4, 0) + ratings.getOrDefault(5, 0);
        // Then
        Assertions.assertEquals(hotelRating, hotels.size());
    }

    @Test
    void itShouldGetHotelsByRatingBetween() {
        // Given
        Set<HotelResponse> hotels = hotelService.getHotelsByRatingBetween(3, 5);
        // When
        var hotelRating = ratings.getOrDefault(3, 0) + ratings.getOrDefault(4, 0) + ratings.getOrDefault(5, 0);
        // Then
        Assertions.assertEquals(hotelRating, hotels.size());
    }

    @Test
    void itShouldGetHotelsByRating() {
        // Given
        Set<HotelResponse> hotels = hotelService.getHotelsByRating(5);
        // When
        var hotelRating = ratings.getOrDefault(5, 0);
        // Then
        Assertions.assertEquals(hotelRating, hotels.size());
    }

    @Test
    void itShouldGetHotelsByCity() {
        // Given
        Random random = new Random();
        cities.entrySet().stream().skip(random.nextInt(cities.size())).findFirst().ifPresent(city -> {
            Set<HotelResponse> hotels = hotelService.getHotelsByCity(city.getKey());
            // When
            var hotelCity = cities.get(city.getKey());
            // Then
            Assertions.assertEquals(hotelCity, hotels.size());
        });
    }

    @Test
    void itShouldGetHotelsByCountry() {
        // Given
        Random random = new Random();
        countries.entrySet().stream().skip(random.nextInt(countries.size())).findFirst().ifPresent(country -> {
            Set<HotelResponse> hotels = hotelService.getHotelsByCountry(country.getKey());
            // When
            var hotelCountry = countries.get(country.getKey());
            // Then
            Assertions.assertEquals(hotelCountry, hotels.size());
        });
    }
}
