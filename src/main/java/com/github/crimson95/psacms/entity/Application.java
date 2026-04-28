package com.github.crimson95.psacms.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "applications")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many applications can belong to one user.
    // This field stores which user submitted the application.
    @ManyToOne
    @JoinColumn(name = "applicant_id", nullable = false)
    private User applicant;

    // Short title or category of the application
    // for example: "2026 Youth Startup Grant".
    @Column(nullable = false)
    private String title;

    // Detailed description written by the applicant.
    @Column(columnDefinition = "TEXT")
    private String description;

    // Stores the latest status directly on the application
    // so the current state can be queried quickly.
    @Column(nullable = false, length = 50)
    private String currentStatus = "DRAFT";

    // Timestamp recorded when the row is first created.
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getApplicant() {
        return applicant;
    }

    public void setApplicant(User applicant) {
        this.applicant = applicant;
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
