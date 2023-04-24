package com.lgzarturo.api.personal.api.auth;

import com.jayway.jsonpath.JsonPath;
import com.lgzarturo.api.personal.api.user.User;
import com.lgzarturo.api.personal.api.user.UserService;
import com.lgzarturo.api.personal.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @BeforeEach
    void setUp() {
        assertNotNull(mockMvc);
        assertNotNull(userService);
    }

    @Test
    void itShouldLogin() throws Exception {
        // Given
        Optional<User> user = userService.getAllUsers().stream().findFirst();
        assertTrue(user.isPresent());
        // When
        RequestBuilder request = MockMvcRequestBuilders.post("/api/v1/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"username\": \""+user.get().getUsername()+"\",\"password\": \"password\"}");
        // Then
        mockMvc.perform(request)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.token").exists());
    }

    @Test
    void itShouldGetAuthenticatedUser() throws Exception {
        // Given
        Optional<User> user = userService.getAllUsers().stream().findFirst();
        assertTrue(user.isPresent());
        AtomicReference<String> tokenJsonObject = new AtomicReference<>("");
        RequestBuilder request = MockMvcRequestBuilders.post("/api/v1/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"username\": \""+user.get().getUsername()+"\",\"password\": \"password\"}");
        mockMvc.perform(request)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(result -> tokenJsonObject.set(result.getResponse().getContentAsString()));
        String token = JsonPath.parse(tokenJsonObject.get()).read("$.token");
        assertNotEquals("", token);
        // When
        request = MockMvcRequestBuilders.get("/api/v1/auth/me")
            .header(Constants.HEADER_AUTHENTICATION, Constants.BEARER_PREFIX + token);
        // Then
        mockMvc.perform(request)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.username").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.roles").exists());
    }
}
