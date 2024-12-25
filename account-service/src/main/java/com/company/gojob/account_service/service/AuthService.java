package com.company.gojob.account_service.service;

import com.company.gojob.account_service.model.UserCredential;
import com.company.gojob.account_service.payload.request.AuthRequest;
import com.company.gojob.account_service.payload.response.LoginResponse;
import com.company.gojob.account_service.payload.response.UserCredentialResponse;
import com.company.gojob.account_service.repository.AuthRepository;
import com.company.gojob.account_service.service.implement.IAuthService;
import com.company.gojob.common_service.config.Trans;
import com.company.gojob.common_service.payload.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService implements IAuthService {
    @Autowired
    AuthRepository authRepository;

    @Autowired
    JwtService jwtService;

    @Autowired
    private UserCredentialService userCredentialService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public boolean createUser(UserCredential userCredential) {
        authRepository.save(userCredential);
        return true;
    }

    @Override
    public LoginResponse authenticationManager(AuthRequest request) {
        String accessToken = generateToken(request.getUsername());
        String refreshToken = jwtService.generateRefreshToken(request.getUsername());
        Map<String, Object> tokenData = generateTokenWithExpiration(request.getUsername());
        Date expirationDate = (Date) tokenData.get("expirationDate");
        LocalDateTime isoExpirationDate = expirationDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        UserCredentialResponse userCredentialResponse = userCredentialService.getUserCredentialByUserName(request.getUsername());
        LoginResponse loginResponse = new LoginResponse(
                accessToken,
                refreshToken,
                isoExpirationDate.toString(),
                userCredentialResponse
        );
        return loginResponse;
    }

    public String generateToken(String userName) {
        return jwtService.generateToken(userName);
    }

    public Map<String, Object> generateTokenWithExpiration(String userName) {
        String token = jwtService.generateToken(userName);
        Date expirationDate = jwtService.getExpirationDateFromToken(token);
        Map<String, Object> tokenData = new HashMap<>();
        tokenData.put("token", token);
        tokenData.put("expirationDate", expirationDate);
        return tokenData;
    }

    public void invalidateToken(String token) {
        jwtService.validateToken(token);
    }

    @Override
    public ResponseEntity<?> authenticateUser(AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        if (!authentication.isAuthenticated()) {
            return BaseResponse.error();
        }
        String accessToken = jwtService.generateToken(request.getUsername());
        String refreshToken = jwtService.generateRefreshToken(request.getUsername());
        Map<String, Object> tokenData = generateTokenWithExpiration(request.getUsername());
        Date expirationDate = (Date) tokenData.get("expirationDate");
        LocalDateTime isoExpirationDate = expirationDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        UserCredentialResponse userCredentialResponse = userCredentialService.getUserCredentialByUserName(request.getUsername());
        LoginResponse loginResponse = new LoginResponse(accessToken, refreshToken, isoExpirationDate.toString(), userCredentialResponse);
        return BaseResponse.success(Trans.getMessage("success.response-message"), loginResponse);
    }

}
