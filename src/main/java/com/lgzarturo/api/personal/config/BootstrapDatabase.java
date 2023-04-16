package com.lgzarturo.api.personal.config;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import com.lgzarturo.api.personal.api.user.Role;
import com.lgzarturo.api.personal.api.user.User;
import com.lgzarturo.api.personal.api.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("development")
@Slf4j
public class BootstrapDatabase implements ApplicationRunner {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public BootstrapDatabase(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("Bootstrapping database");
        createRandomUser(userService, passwordEncoder);
    }

    private static void createRandomUser(
        UserService userService,
        PasswordEncoder passwordEncoder
    ) {
        log.info("Creating random user");
        var faker = new Faker();
        Name name = faker.name();
        String firstName = name.firstName();
        String lastName = name.lastName();
        String email = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@example.com";
        String password = passwordEncoder.encode("password");
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setActive(true);
        user.setRole(List.of(Role.ROLE_ADMIN));
        userService.create(user);
        log.info("Created user: {}", email);
    }
}
