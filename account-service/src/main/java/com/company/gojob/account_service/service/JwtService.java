package com.company.gojob.account_service.service;

import com.company.gojob.common_service.config.Trans;
import com.company.gojob.common_service.payload.response.BaseResponse;
import com.fasterxml.jackson.databind.ser.Serializers;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    // Validate token
    public void validateToken(final String token) {
        try {
            // Loại bỏ "Bearer " từ token
            String jwt = token.startsWith("Bearer ") ? token.substring(7) : token;

            Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(jwt);
        } catch (Exception e) {
            throw new RuntimeException("Invalid token or token has expired", e);
        }
    }

    // Generate access token
    public String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", userName);
        return createToken(claims, userName, 1000 * 60 * 30); // Expire in 30 minutes
    }

    // Generate refresh token (longer expiry)
    public String generateRefreshToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", userName);
        return createToken(claims, userName, 1000 * 60 * 60 * 24 * 7); // Expire in 7 days
    }

    public ResponseEntity<?> refreshTokens(String refreshToken) {
        if (refreshToken == null || isTokenExpired(refreshToken)) {
            return BaseResponse.error("Invalid or expired refresh token.");
        }

        String username = extractClaims(refreshToken).getSubject();
        String newAccessToken = generateToken(username);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", newAccessToken);
        tokens.put("refreshToken", refreshToken);

        return BaseResponse.success(Trans.getMessage("success.response-message"), tokens);
    }

    public Date getExpirationDateFromToken(String token) {
        Claims claims = extractClaims(token);
        return claims.getExpiration();
    }

    // Create token
    public String createToken(Map<String, Object> claims, String userName, long expirationTime) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Check if token is expired
    public boolean isTokenExpired(String token) {
        try {
            Claims claims = extractClaims(token);
            return claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return true; // Token invalid or expired
        }
    }

    // Extract claims from token
    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Get signing key
    public Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
