package com.lgzarturo.api.personal.config;

import com.lgzarturo.api.personal.api.user.User;
import com.lgzarturo.api.personal.api.user.UserService;
import com.lgzarturo.api.personal.utils.Helpers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

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
        String password = passwordEncoder.encode("password");
        User user = Helpers.getRandomUser(password);
        userService.create(user);
        assert user.getId() != null;
        log.info("Created user: {}", user.getEmail());
    }
}
