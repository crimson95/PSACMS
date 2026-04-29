package com.github.crimson95.psacms.dto;

import java.time.LocalDateTime;

// DTO used to return application data to the client.
// This prevents us from exposing the full Application or User entity directly.
public class ApplicationResponse {
    private Long id;
    // Stores the applicant's username for display in the response.
    private String applicationName;
    private String title;
    private String CurrentStatus;
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCurrentStatus() {
        return CurrentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        CurrentStatus = currentStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
