package com.lgzarturo.api.personal.api.hotel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lgzarturo.api.personal.api.auth.dto.LoginRequest;
import com.lgzarturo.api.personal.api.auth.dto.LoginResponse;
import com.lgzarturo.api.personal.api.user.User;
import com.lgzarturo.api.personal.api.user.UserService;
import com.lgzarturo.api.personal.utils.Helpers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class HotelControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private HotelRepository hotelRepository;
    private final HttpHeaders headers = new HttpHeaders();
    Map<String, Integer> cities = new HashMap<>();
    Map<String, Integer> countries = new HashMap<>();
    Map<Integer, Integer> ratings = new HashMap<>();

    @BeforeEach
    void setUp() throws Exception {
        hotelRepository.deleteAll();
        assertNotNull(mockMvc);
        List<Hotel> hotels = Helpers.getRandomHotels(20);
        for (Hotel hotel : hotels) {
            var city = hotel.getHotelAddress().getAddress().getCity();
            var country = hotel.getHotelAddress().getAddress().getCountry();
            var rating = hotel.getRating();
            if (cities.containsKey(city)) {
                cities.put(city, cities.get(city) + 1);
            } else {
                cities.put(hotel.getHotelAddress().getAddress().getCity(), 1);
            }
            if (countries.containsKey(country)) {
                countries.put(country, countries.get(country) + 1);
            } else {
                countries.put(country, 1);
            }
            if (ratings.containsKey(rating)) {
                ratings.put(rating, ratings.get(rating) + 1);
            } else {
                ratings.put(rating, 1);
            }
        }
        hotelRepository.saveAll(hotels);
        String username = "lgzarturo@gmail.com";
        String password = "password";
        if (!userService.existsUserByEmail(username)) {
            User user = Helpers.getAdminUser(username, passwordEncoder.encode(password));
            userService.create(user);
        }
        LoginRequest loginRequest = new LoginRequest(username, password);
        RequestBuilder request = MockMvcRequestBuilders.post("/api/v1/auth/login")
            .content(objectMapper.writeValueAsString(loginRequest))
            .header("Content-Type", "application/json");
        LoginResponse loginResponse = objectMapper.readValue(
            mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString(),
            LoginResponse.class
        );
        String token = loginResponse.token();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "Bearer " + token);
    }

    @Test
    void itShouldGetAll() throws Exception {
        // Given
        // When
        var response = MockMvcRequestBuilders.get("/api/v1/hotels")
            .headers(headers);
        // Then
        mockMvc.perform(response)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.content.length()").value(10))
            .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElements").value(10))
            .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(20));
    }

    @Test
    void itShouldGetHotelsByRating() throws Exception {
        // Given
        Random random = new Random();
        int rating = ratings.keySet().stream().skip(random.nextInt(ratings.size())).findFirst().orElseThrow();
        // When
        var response = MockMvcRequestBuilders.get("/api/v1/hotels/rating/"+rating)
            .headers(headers);
        // Then
        mockMvc.perform(response)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(ratings.get(rating)));
    }

    @Test
    void itShouldGetHotelsByCity() throws Exception {
        // Given
        Random random = new Random();
        String city = cities.keySet().stream().skip(random.nextInt(cities.size())).findFirst().orElseThrow();
        // When
        var response = MockMvcRequestBuilders.get("/api/v1/hotels/city/"+city)
            .headers(headers);
        // Then
        mockMvc.perform(response)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(cities.get(city)));
    }

    @Test
    void itShouldGetHotelsByCountry() throws Exception {
        // Given
        Random random = new Random();
        String country = countries.keySet().stream().skip(random.nextInt(countries.size())).findFirst().orElseThrow();
        // When
        var response = MockMvcRequestBuilders.get("/api/v1/hotels/country/"+country)
            .headers(headers);
        // Then
        mockMvc.perform(response)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(countries.get(country)));
    }

    @Test
    void itShouldGet() throws Exception {
        // Given
        var hotel = getFirstHotel();
        // When
        var response = MockMvcRequestBuilders.get("/api/v1/hotels/"+hotel.getId())
            .headers(headers);
        // Then
        mockMvc.perform(response)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(hotel.getId()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(hotel.getName()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.rating").value(hotel.getRating()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.minimumPrice").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.maximumPrice").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.address").exists());
    }

    @Test
    void itShouldActive() throws Exception {
        // Given
        var hotel = getFirstHotel();
        // When
        var response = MockMvcRequestBuilders.patch("/api/v1/hotels/"+hotel.getId()+"/active")
            .headers(headers);
        // Then
        mockMvc.perform(response)
            .andExpect(MockMvcResultMatchers.status().isAccepted());
        var hotelPersisted = hotelRepository.findById(Objects.requireNonNull(hotel.getId()));
        assertTrue(hotelPersisted.isPresent());
        assertTrue(hotelPersisted.get().getIsActive());
    }

    @Test
    void itShouldInactive() throws Exception {
        // Given
        var hotel = getFirstHotel();
        var response = MockMvcRequestBuilders.patch("/api/v1/hotels/"+hotel.getId()+"/inactive")
            .headers(headers);
        // Then
        mockMvc.perform(response)
            .andExpect(MockMvcResultMatchers.status().isAccepted());
        var hotelPersisted = hotelRepository.findById(Objects.requireNonNull(hotel.getId()));
        assertTrue(hotelPersisted.isPresent());
        assertFalse(hotelPersisted.get().getIsActive());
    }

    private Hotel getFirstHotel() {
        return hotelRepository.findAll().stream().iterator().next();
    }
}
