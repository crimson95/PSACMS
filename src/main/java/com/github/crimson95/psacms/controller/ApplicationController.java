package com.github.crimson95.psacms.controller;

import com.github.crimson95.psacms.dto.ApplicationCreateRequest;
import com.github.crimson95.psacms.entity.Application;
import com.github.crimson95.psacms.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @PostMapping("/submit")
    public String submitApplication(@RequestBody ApplicationCreateRequest request) {
        applicationService.submitApplication(request);
        return "Application submitted successfully! Audit trail has been generated.";
    }
}
