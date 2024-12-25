package com.company.gojob.account_service.service.implement;

import com.company.gojob.account_service.model.UserCredential;
import com.company.gojob.account_service.payload.request.UserCreateRequest;
import com.company.gojob.account_service.payload.response.UserCredentialResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IUserCredentialService {
    List<UserCredential> getAllUserCredentials();

    ResponseEntity<?> registerUser(UserCreateRequest userCreateRequest);

    Page<UserCredential> findAllUserCredentials(Pageable pageable);

    Page<UserCredential> getAllUserCredentialSearch(Pageable pageable, String typeSearch, String valueSearch);

    UserCredential getUserCredentialById(String id);

    UserCredentialResponse getUserCredentialByUserName(String username);

    boolean getUserCredentialByUserNameOrEmailOrPhone(String username, String email, String phoneNumber);

    boolean getUserCredentialByUserNameOrEmail(String username, String email);

    int updateUserCredentialById(String id, String email, String username);

    int deleteUserCredentialById(String id);

    UserCredential saveEntity(UserCredential userCredential);
}
