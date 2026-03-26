package com.example.employee_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.employee_management.JwtTokenProvider;
import com.example.employee_management.model.User;
import com.example.employee_management.payload.AuthResponse;
import com.example.employee_management.payload.LoginRequest;
import com.example.employee_management.repository.UserRepository;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtTokenProvider tokenProvider;

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        // Mã hóa mật khẩu trước khi lưu
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User registered successfully!";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // Giả sử xác thực thành công (nên dùng AuthenticationManager)
        String jwt = tokenProvider.generateToken(loginRequest.getUsername());
        User user = userRepository.findByUsername(loginRequest.getUsername()).get();
        
        // Trả về Token và Role để FE xử lý UI
        return ResponseEntity.ok(new AuthResponse(jwt, user.getRole()));
    }
}
