package com.lgzarturo.api.personal.api.user;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    Optional<User> getUserById(Long id);
    Optional<User> getUserByEmail(String email);
    boolean existsUserByEmail(String email);
    User create(User user);
}
