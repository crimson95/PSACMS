package com.github.crimson95.psacms.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity  // 告訴 Spring JPA：這個類別對應到資料庫的一張表
@Table(name = "users")  // 指定資料庫的表名為 users
public class User {

    @Id  // 這代表 Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 這代表 AUTO_INCREMENT
    private Long id;

    @Column(nullable = false, unique = true)  // NOT NULL, UNIQUE
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(nullable = false, length = 50)
    private String role;

    // 建立時間，當資料新增時自動寫入當下時間
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Long getId() { return id; }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
