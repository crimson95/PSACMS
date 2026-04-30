package com.github.crimson95.psacms.dto;

// DTO for capturing status update requests from officers
public class ApplicationStatusUpdateRequest {

    private String newStatus;  // e.g., "UNDER_REVIEW", "APPROVED", "REJECTED"
    private String comments;  // Optional remarks from the officer

    public String getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
