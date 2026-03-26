package com.example.employee_management;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;

@Component
public class JwtTokenProvider {

    // Lưu ý: Chuỗi bí mật phải đủ dài (ít nhất 64 ký tự cho HS512)
    private final String JWT_SECRET = "Your_Very_Long_And_Secure_Secret_Key_For_HS512_Algorithm_123456789_Extra_Characters";
    private final long JWT_EXPIRATION = 86400000L;

    // Tạo Key an toàn từ chuỗi String
    private Key getSigningKey() {
        byte[] keyBytes = JWT_SECRET.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // TẠO TOKEN
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    // GIẢI MÃ TOKEN
    public String getUsernameFromJWT(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // KIỂM TRA TOKEN HỢP LỆ
    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(authToken);
            return true;
        } catch (Exception ex) {
            // Có thể log lỗi cụ thể như ExpiredJwtException, MalformedJwtException...
            return false;
        }
    }
}
