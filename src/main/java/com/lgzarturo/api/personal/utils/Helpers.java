package com.lgzarturo.api.personal.utils;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import com.lgzarturo.api.personal.api.user.Role;
import com.lgzarturo.api.personal.api.user.User;

import java.util.List;

public class Helpers {

    public static String generateEmail() {
        var faker = new Faker();
        Name name = faker.name();
        String firstName = name.firstName();
        String lastName = name.lastName();
        return firstName.toLowerCase() + "." + lastName.toLowerCase() + "@example.com";
    }

    public static User getRandomUser(String encodedPassword) {
        User user = new User();
        user.setEmail(generateEmail());
        user.setPassword(encodedPassword);
        user.setActive(true);
        user.setRole(List.of(Role.ROLE_ADMIN));
        return user;
    }
}
