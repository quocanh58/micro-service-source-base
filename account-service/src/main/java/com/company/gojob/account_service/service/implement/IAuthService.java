package com.company.gojob.account_service.service.implement;

import com.company.gojob.account_service.model.UserCredential;
import com.company.gojob.account_service.payload.request.AuthRequest;
import com.company.gojob.account_service.payload.response.LoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface IAuthService {
    public boolean createUser(UserCredential userCredential);

    public LoginResponse authenticationManager(AuthRequest request);

    public ResponseEntity<?> authenticateUser(AuthRequest request);
}
