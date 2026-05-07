package com.github.crimson95.psacms.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController  // Marks this class as a controller that returns HTTP responses directly.
public class HomeController {

    @GetMapping({"", "/"})  // Accept both the context root with and without a trailing slash.
    public String home() {
        return "Welcome to PSACMS. The server and database are functional.";
    }
}
