package com.github.crimson95.psacms.service;

import com.github.crimson95.psacms.dto.UserCreateRequest;
import com.github.crimson95.psacms.entity.User;
import com.github.crimson95.psacms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service  // Marks this class as the service layer for business logic.
public class UserService {

    // Repository used to persist and query users.
    @Autowired
    private UserRepository userRepository;

    // Inject the password encoder defined in SecurityConfig.
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
