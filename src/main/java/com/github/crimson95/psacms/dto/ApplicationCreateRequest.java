package com.github.crimson95.psacms.dto;

public class ApplicationCreateRequest {

    private Long applicantId;  // 告訴後端是哪位市民(User)送出的申請
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
