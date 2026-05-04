package com.github.crimson95.psacms.dto;

// DTO for the JSON body sent by citizen.html when creating an application.
// The applicant ID is intentionally not included; the backend uses the authenticated JWT user instead.
public class ApplicationCreateRequest {
    private String title;
    private String description;

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
