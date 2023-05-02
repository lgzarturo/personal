package com.lgzarturo.api.personal.utils;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import com.lgzarturo.api.personal.api.user.Role;
import com.lgzarturo.api.personal.api.user.User;

import java.util.List;
import java.util.stream.Stream;

public class Helpers {

    public static String generateEmail() {
        var faker = new Faker();
        Name name = faker.name();
        String firstName = name.firstName();
        String lastName = name.lastName();
        return firstName.toLowerCase() + "." + lastName.toLowerCase() + "@example.com";
    }

    public static User getRandomUser(String encodedPassword) {
        return getRandomUserWithRoles(encodedPassword, List.of(Role.ROLE_USER));
    }

    public static User getRandomUserWithRoles(String encodedPassword, List<Role> roles) {
        return User.builder()
            .email(generateEmail())
            .password(encodedPassword)
            .isActive(true)
            .role(roles)
            .build();
    }

    public static User getAdminUser(String email, String encodedPassword) {
        return User.builder()
            .email(email)
            .password(encodedPassword)
            .isActive(true)
            .role(List.of(Role.ROLE_ADMIN))
            .build();
    }

    public static List<User> getRandomUsers(int count, String encodedPassword) {
        return Stream.of(new User[count]).map(user -> getRandomUser(encodedPassword)).toList();
    }
}
