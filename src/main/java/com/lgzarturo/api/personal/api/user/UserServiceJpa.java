package com.lgzarturo.api.personal.api.user;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceJpa implements UserService {

    private final UserRepository userRepository;

    public UserServiceJpa(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public boolean existsUserByEmail(String email) {
        return userRepository.existsUserByEmail(email);
    }

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }
}
