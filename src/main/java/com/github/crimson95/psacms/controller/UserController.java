package com.github.crimson95.psacms.controller;

import com.github.crimson95.psacms.dto.UserCreateRequest;
import com.github.crimson95.psacms.entity.User;
import com.github.crimson95.psacms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/create-test")
    public String createTestUser(){
        User newUser = new User();
        newUser.setUsername("test_citizen_" + System.currentTimeMillis() + "@example.com");
        newUser.setPassword("dummy_password");
        newUser.setRole("CITIZEN");

        userRepository.save(newUser);

        return "Success, User save in MySQL database.";
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody UserCreateRequest request){
        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setPassword(request.getPassword() + "_hashed");
        newUser.setRole(request.getRole());
        userRepository.save(newUser);
        return "Success, User " + request.getUsername() + "save in MySQL database.";
    }
}
