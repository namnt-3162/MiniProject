package com.example.employee_management.payload; // Thay bằng package của bạn

public class LoginRequest {
    private String username;
    private String password;

    // Quan trọng: Phải có Constructor mặc định để Jackson có thể parse JSON
    public LoginRequest() {
    }

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getter và Setter (Thiếu cái này sẽ gây lỗi undefined)
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
}

