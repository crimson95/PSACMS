package com.github.crimson95.psacms.dto;

public class ApplicationCreateRequest {

    // The ID of the user who is submitting this application.
    private Long applicantId;
    private String title;
    private String description;

    public Long getApplicantId() { return applicantId; }

    public void setApplicantId(Long applicantId) {
        this.applicantId = applicantId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
