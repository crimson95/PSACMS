package com.github.crimson95.psacms.service;

import com.github.crimson95.psacms.dto.ApplicationCreateRequest;
import com.github.crimson95.psacms.dto.ApplicationResponse;
import com.github.crimson95.psacms.dto.ApplicationStatusUpdateRequest;
import com.github.crimson95.psacms.entity.Application;
import com.github.crimson95.psacms.entity.ApplicationStatusHistory;
import com.github.crimson95.psacms.entity.User;
import com.github.crimson95.psacms.repository.ApplicationRepository;
import com.github.crimson95.psacms.repository.ApplicationStatusHistoryRepository;
import com.github.crimson95.psacms.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationService {

    // Repositories are used here to load and save database entities.
    // The service layer coordinates business rules across one or more repositories.
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private ApplicationStatusHistoryRepository historyRepository;
    @Autowired
    private UserRepository userRepository;

    // Ensures atomic execution: both database operations succeed, or both rollback entirely.
    @Transactional
    public Application submitApplication(ApplicationCreateRequest request) {
        // 1. Load the applicant from the authenticated user instead of trusting a client-supplied ID.
        // The JWT filter already placed the username into Spring Security's context for this request.
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User applicant = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("Logged in user not found in DB"));

        // 2. Create and persist the main Application record
        Application app = new Application();
        app.setApplicant(applicant);
        app.setTitle(request.getTitle());
        app.setDescription(request.getDescription());
        app.setCurrentStatus("SUBMITTED");

        Application savedApp = applicationRepository.save(app);

        // 3. Create and persist the Audit Trail (Status History) record
        ApplicationStatusHistory history = new ApplicationStatusHistory();
        history.setApplication(savedApp);
        history.setFromStatus(null);  // No previous status for a newly created application
        history.setToStatus("SUBMITTED");
        // Record who performed the action so the history remains traceable.
        history.setActor(applicant);
        history.setComments("The application has been submitted");

        historyRepository.save(history);

        // 4. Return the successfully saved entity
        return savedApp;
    }

    // Converts a JPA entity into a DTO.
    // Controllers return DTOs so the API exposes only the fields the frontend needs.
    public ApplicationResponse toResponse(Application app) {
        ApplicationResponse response = new ApplicationResponse();
        response.setId(app.getId());
        response.setApplicantName(app.getApplicant().getUsername());
        response.setTitle(app.getTitle());
        response.setCurrentStatus(app.getCurrentStatus());
        response.setCreatedAt(app.getCreatedAt());
        return response;
    }

    // Used by the citizen portal to reload persisted submissions after page refresh.
    public List<ApplicationResponse> getCurrentUserApplications() {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User applicant = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("Logged in user not found in DB"));

        return applicationRepository.findByApplicantOrderByCreatedAtDesc(applicant)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // Retrieves a list of applications filtered by their current status
    public List<ApplicationResponse> getApplicationsByStatus(String status) {

        // 1. Load all application entities whose currentStatus matches the input.
        List<Application> applications = applicationRepository.findByCurrentStatus(status);

        // 2. Convert each entity into a response DTO.
        // Returning DTOs is safer than returning JPA entities directly,
        // because we control exactly which fields are exposed to the client.
        return applications.stream().map(this::toResponse).toList();
    }

    // Processes state transitions and generates an audit trail entry atomically
    @Transactional
    public Application updateApplicationStatus(Long applicationId, ApplicationStatusUpdateRequest request) {

        // 1. Retrieve the Application
        Application app = applicationRepository.findById(applicationId).orElseThrow(() -> new RuntimeException("Application Not Found"));

        // 2. Retrieve the Officer (Actor)
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User officer = userRepository.findByUsername(currentUsername).orElseThrow(() -> new RuntimeException("Logged in user not found in DB"));

        // 3. State Machine Validation (Prevent redundant updates)
        String oldStatus = app.getCurrentStatus();
        if(oldStatus.equals(request.getNewStatus())){
            throw new RuntimeException("Application is already in the requested status: " + oldStatus);
        }

        // 4. Update the main Application state
        app.setCurrentStatus(request.getNewStatus());
        Application updatedApp = applicationRepository.save(app);

        // 5. Record the Audit Trail (Status History)
        // The history table keeps past changes even though Application.currentStatus stores only the latest status.
        ApplicationStatusHistory history = new ApplicationStatusHistory();
        history.setApplication(updatedApp);
        history.setFromStatus(oldStatus);  // Snapshot of previous state
        history.setToStatus(request.getNewStatus());  // The new state
        history.setActor(officer);  // The person who made the change
        history.setComments(request.getComments());

        historyRepository.save(history);

        return updatedApp;
    }
}
