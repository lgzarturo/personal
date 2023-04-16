package com.lgzarturo.api.personal.api.user;

import java.util.Optional;

public interface UserService {
    Optional<User> getUserByEmail(String email);
    boolean existsUserByEmail(String email);
    User create(User user);
}
