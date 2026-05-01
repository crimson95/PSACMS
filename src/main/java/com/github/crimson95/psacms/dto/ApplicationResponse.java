package com.github.crimson95.psacms.dto;

import java.time.LocalDateTime;

// DTO used to return application data to the client.
// This prevents us from exposing the full Application or User entity directly.
public class ApplicationResponse {
    private Long id;
    // Stores the applicant's username for display in the response.
    private String applicantName;
    private String title;
    private String currentStatus;
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
