package com.github.crimson95.psacms.controller;

import com.github.crimson95.psacms.dto.UserCreateRequest;
import com.github.crimson95.psacms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")  // Every endpoint in this controller starts with /api/users.
@CrossOrigin(origins = "*")  // Unlock cross-domain restrictions
public class UserController {

    // Dependency injection: Spring creates UserService and provides it here.
    @Autowired
    private UserService userService;

    // This endpoint accepts a JSON request body and creates a new user.
    @PostMapping("/register")
    public String registerUser(@RequestBody UserCreateRequest request){

        // The controller should stay thin:
        // 1. Receive the HTTP request and pass the data to the service layer.
        userService.registerUser(request);

        // 2. Return a response message to the client.
        return "Registration successful! User [" + request.getUsername() + "] has been securely saved.";
    }
}
