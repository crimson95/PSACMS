package com.github.crimson95.psacms.controller;

import com.github.crimson95.psacms.dto.ApplicationCreateRequest;
import com.github.crimson95.psacms.dto.ApplicationResponse;
import com.github.crimson95.psacms.dto.ApplicationStatusUpdateRequest;
import com.github.crimson95.psacms.entity.Application;
import com.github.crimson95.psacms.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")  // Every endpoint in this controller starts with /api/applications.
public class ApplicationController {

    // Inject the service layer that contains the application submission logic.
    @Autowired
    private ApplicationService applicationService;

    // Accepts a JSON payload and submits a new application.
    @PostMapping("/submit")
    public String submitApplication(@RequestBody ApplicationCreateRequest request) {
        applicationService.submitApplication(request);
        return "Application submitted successfully! Audit trail has been generated.";
    }

    // Example request: GET /api/applications/status/SUBMITTED
    @GetMapping("/status/{status}")
    public List<ApplicationResponse> getApplicationByStatus(@PathVariable String status) {
        // @PathVariable reads the {status} value from the URL
        // and passes it into this method as the "status" argument.
        return applicationService.getApplicationsByStatus(status);
    }

    // PUT /api/applications/1/status
    // Updates the status of a specific application and logs the transition
    @PutMapping("/{id}/status")
    public String updateStatus(@PathVariable Long id, @RequestBody ApplicationStatusUpdateRequest request) {
        applicationService.updateApplicationStatus(id, request);
        return "Status successfully updated to [" + request.getNewStatus() + "]. Audit log recorded.";
    }
}
