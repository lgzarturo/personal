package com.lgzarturo.api.personal.api.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class WebControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        assertNotNull(mockMvc);
    }

    @Test
    void itShouldPing() throws Exception {
        // When
        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/web/ping");
        // Then
        mockMvc.perform(request)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists());
    }

    @Test
    void itShouldInfo() throws Exception {
        // When
        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/web/info");
        // Then
        mockMvc.perform(request)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.version").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.description").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.url").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").exists());
    }

    @Test
    void itShouldGetPosts() throws Exception {
        // When
        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/web/posts");
        // Then
        mockMvc.perform(request)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].userId").exists())
            .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
            .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

    @Test
    void itShouldGetPostById() throws Exception {
        // When
        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/web/posts/1");
        // Then
        mockMvc.perform(request)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.userId").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.body").exists());
    }
}
