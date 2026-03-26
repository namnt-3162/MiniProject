package com.example.employee_management.payload;

public class AuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private String role; // Để FE biết là ADMIN hay USER mà ẩn/hiện nút

    // Constructor
    public AuthResponse(String accessToken, String role) {
        this.accessToken = accessToken;
        this.role = role;
    }

    // Getter và Setter (Bắt buộc phải có để Spring chuyển thành JSON)
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
