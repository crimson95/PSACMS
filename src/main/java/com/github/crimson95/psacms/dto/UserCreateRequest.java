package com.github.crimson95.psacms.dto;

// 這個類別專門用來接收前端傳來的註冊 JSON 資料
public class UserCreateRequest {

    private String username;
    private String password;  // 接收明碼密碼
    private String role;

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
}
