package com.lgzarturo.api.personal.api.user;

import com.lgzarturo.api.personal.utils.Helpers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserServiceJpa(userRepository);
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void itShouldGetUserByEmail() {
        // Given
        User user = Helpers.getRandomUser(passwordEncoder.encode("password"));
        userRepository.save(user);
        // When
        Optional<User> persistedUser = userService.getUserByEmail(user.getEmail());
        // Then
        assertTrue(persistedUser.isPresent());
    }

    @Test
    void itShouldExistsUserByEmail() {
        // Given
        User user = Helpers.getRandomUser(passwordEncoder.encode("password"));
        userRepository.save(user);
        // When
        boolean exists = userService.existsUserByEmail(user.getEmail());
        // Then
        assertTrue(exists);
    }

    @Test
    void itShouldCreate() {
        // Given
        User user = Helpers.getRandomUser(passwordEncoder.encode("password"));
        // When
        userService.create(user);
        // Then
        Optional<User> persistedUser = userService.getUserByEmail(user.getEmail());
        assertTrue(persistedUser.isPresent());
    }
}
