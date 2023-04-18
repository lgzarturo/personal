package com.lgzarturo.api.personal.config;

import com.lgzarturo.api.personal.api.user.Role;
import com.lgzarturo.api.personal.api.user.User;
import com.lgzarturo.api.personal.api.user.UserService;
import com.lgzarturo.api.personal.utils.Helpers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class BootstrapDatabase implements ApplicationRunner {

    private final AppConfigProperties appConfigProperties;
    private final Environment environment;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public BootstrapDatabase(
        AppConfigProperties appConfigProperties,
        Environment environment,
        UserService userService,
        PasswordEncoder passwordEncoder
    ) {
        this.appConfigProperties = appConfigProperties;
        this.environment = environment;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("Bootstrapping database");
        if (environment.acceptsProfiles(Profiles.of("production"))) {
            createAdministrator(userService, passwordEncoder, appConfigProperties);
        } else {
            createRandomAdministrator(userService, passwordEncoder);
        }
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

    private static void createRandomAdministrator(
        UserService userService,
        PasswordEncoder passwordEncoder
    ) {
        log.info("Creating random administrator");
        String password = passwordEncoder.encode("password");
        User user = Helpers.getRandomUserWithRoles(password, List.of(Role.ROLE_ADMIN, Role.ROLE_USER));
        userService.create(user);
        assert user.getId() != null;
        log.info("Created user administrator: {}", user.getEmail());
    }

    private static void createAdministrator(
        UserService userService,
        PasswordEncoder passwordEncoder,
        AppConfigProperties appConfigProperties
    ) {
        log.info("Creating user administrator");
        if (userService.existsUserByEmail(appConfigProperties.admin().username())) {
            log.info("User administrator '{}' already exists", appConfigProperties.admin().username());
            return;
        }
        String password = passwordEncoder.encode(appConfigProperties.admin().password());
        User user = Helpers.getAdminUser(appConfigProperties.admin().username(), password);
        userService.create(user);
        assert user.getId() != null;
        log.info("Created user administrator: {}", user.getEmail());
    }
}
