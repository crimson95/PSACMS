package com.github.crimson95.psacms.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "application_status_history")
public class ApplicationStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many history records can belong to one application.
    // Each row tracks one status change for that application.
    @ManyToOne
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;

    // Status before the change, for example: DRAFT.
    @Column(name = "from_status", length = 50)
    private String fromStatus;

    // Status after the change, for example: SUBMITTED.
    @Column(name = "to_status", length = 50)
    private String toStatus;

    // The user who performed this action.
    // This could be the applicant or a reviewer.
    @ManyToOne
    @JoinColumn(name = "actor_id", nullable = false)
    private User actor;

    // Optional note about the status change,
    // for example: "Missing documents, please resubmit."
    @Column(columnDefinition = "TEXT")
    private String comments;

    // Timestamp recorded when this history row is created.
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt =  LocalDateTime.now();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public String getFromStatus() {
        return fromStatus;
    }

    public void setFromStatus(String fromStatus) {
        this.fromStatus = fromStatus;
    }

    public String getToStatus() {
        return toStatus;
    }

    public void setToStatus(String toStatus) {
        this.toStatus = toStatus;
    }

    public User getActor() {
        return actor;
    }

    public void setActor(User actor) {
        this.actor = actor;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
