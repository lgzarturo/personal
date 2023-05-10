package com.lgzarturo.api.personal.api.ticket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lgzarturo.api.personal.api.auth.dto.LoginRequest;
import com.lgzarturo.api.personal.api.auth.dto.LoginResponse;
import com.lgzarturo.api.personal.api.customer.CustomerRepository;
import com.lgzarturo.api.personal.api.flight.FlightRepository;
import com.lgzarturo.api.personal.api.ticket.dto.TicketRequest;
import com.lgzarturo.api.personal.api.ticket.dto.TicketResponse;
import com.lgzarturo.api.personal.api.user.User;
import com.lgzarturo.api.personal.api.user.UserService;
import com.lgzarturo.api.personal.utils.Helpers;
import org.junit.jupiter.api.*;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TicketControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private FlightRepository flightRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
    @Autowired
    private MockMvc mockMvc;
    private final HttpHeaders headers = new HttpHeaders();
    private TicketResponse ticketResponse;
    private Ticket ticketToDelete;

    @BeforeEach
    void setUp() throws Exception {
        ticketRepository.deleteAll();
        customerRepository.deleteAll();
        flightRepository.deleteAll();
        assertNotNull(mockMvc);
        for (int i = 0; i < 5; i++) {
            customerRepository.save(Helpers.getRandomCustomer());
            flightRepository.save(Helpers.getRandomFlight());
        }
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

        if (ticketRepository.count() == 0) {
            var customerId = customerRepository.findAll().iterator().next().getId();
            var flightId = flightRepository.findAll().iterator().next().getId();
            ticketResponse = ticketService.create(TicketRequest.builder().customerId(customerId).flightId(flightId).build());
        }
        ticketToDelete = ticketRepository.save(Helpers.getRandomTicket(null));
    }


    @Order(1)
    @Test
    void itShouldPost() throws Exception {
        // Given
        TicketRequest ticketRequest = TicketRequest.builder()
            .customerId(1L)
            .flightId(1L)
            .build();
        // When
        RequestBuilder request = MockMvcRequestBuilders.post("/api/v1/tickets")
            .headers(headers).content(objectMapper.writeValueAsString(ticketRequest));
        // Then
        mockMvc.perform(request)
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.flight.id").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.flight.flightNumber").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.arrivalDate").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.departureDate").exists());
    }

    @Order(2)
    @Test
    void itShouldGet() throws Exception {
        // Given
        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/tickets/" + ticketResponse.getId())
            .headers(headers);
        // When
        // Then
        mockMvc.perform(request)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.flight").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.arrivalDate").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.departureDate").exists());
    }

    @Order(3)
    @Test
    void itShouldPut() throws Exception {
        // Given
        var flightId = flightRepository.findAll().iterator().next().getId();
        TicketRequest ticketRequest = TicketRequest.builder()
            .customerId(1L)
            .flightId(flightId)
            .build();
        // When
        RequestBuilder request = MockMvcRequestBuilders.put("/api/v1/tickets/" + ticketResponse.getId())
            .headers(headers).content(objectMapper.writeValueAsString(ticketRequest));
        // Then
        mockMvc.perform(request)
            .andExpect(MockMvcResultMatchers.status().isAccepted())
            .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.flight.id").value(flightId))
            .andExpect(MockMvcResultMatchers.jsonPath("$.flight.flightNumber").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.arrivalDate").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.departureDate").exists());
    }

    @Order(4)
    @Test
    void itShouldDelete() throws Exception {
        // Given
        RequestBuilder request = MockMvcRequestBuilders.delete("/api/v1/tickets/" + ticketToDelete.getId())
            .headers(headers);
        // When
        // Then
        mockMvc.perform(request)
            .andExpect(MockMvcResultMatchers.status().isNoContent())
            .andExpect(MockMvcResultMatchers.content().string(""));
    }
}
