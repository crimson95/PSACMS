package com.github.crimson95.psacms.service;

import com.github.crimson95.psacms.dto.UserCreateRequest;
import com.github.crimson95.psacms.entity.User;
import com.github.crimson95.psacms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service  // 告訴 Spring 這是一位負責商業邏輯的「主廚」
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // 把剛剛在 SecurityConfig 建立的加密器「注入」進來
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Handles the business logic for user registration
    public User registerUser(UserCreateRequest request) {

        // 1. Convert DTO to Entity
        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setRole(request.getRole());

        // 2. Hash the plain-text password using BCrypt before persisting to the database
        String password = passwordEncoder.encode(request.getPassword());
        newUser.setPassword(password);

        // 3. Persist to database
        return userRepository.save(newUser);
    }
}
