package com.github.crimson95.psacms.service;

import com.github.crimson95.psacms.dto.ApplicationCreateRequest;
import com.github.crimson95.psacms.entity.Application;
import com.github.crimson95.psacms.entity.ApplicationStatusHistory;
import com.github.crimson95.psacms.entity.User;
import com.github.crimson95.psacms.repository.ApplicationRepository;
import com.github.crimson95.psacms.repository.ApplicationStatusHistoryRepository;
import com.github.crimson95.psacms.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {

    // Repositories are used here to load and save database entities.
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private ApplicationStatusHistoryRepository historyRepository;
    @Autowired
    private UserRepository userRepository;

    // Ensures atomic execution: both database operations succeed, or both rollback entirely.
    @Transactional
    public Application submitApplication(ApplicationCreateRequest request) {

        // 1. Load the applicant from the database using the incoming applicantId.
        // In a real authenticated system, this usually comes from the security context instead.
        User applicant = userRepository.findById(request.getApplicantId()).orElseThrow(() -> new RuntimeException("Application Not Found")) ;

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
}
