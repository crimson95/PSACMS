package com.github.crimson95.psacms.dto;

// DTO used to receive registration data from the client.
public class UserCreateRequest {

    private String username;
    // Receives the plain-text password before it is hashed in the service layer.
    private String password;
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
