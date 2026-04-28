package com.github.crimson95.psacms.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "applications")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 關聯到我們之前寫好的 User 表！代表「是哪個申請人送出的」
    @ManyToOne
    @JoinColumn(name = "applicant_id", nullable = false)
    private User applicant;

    // 申請案的標題或類型 (例如: "2026 青年創業補助")
    @Column(nullable = false)
    private String title;

    // 申請案的詳細內容
    @Column(columnDefinition = "TEXT")
    private String description;

    // 目前的最新狀態 (方便快速查詢，例如: DRAFT, SUBMITTED, APPROVED)
    @Column(nullable = false, length = 50)
    private String currentStatus = "DRAFT";

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
