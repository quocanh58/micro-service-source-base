package com.company.gojob.account_service.service;

import com.company.gojob.account_service.mapper.UserCredentialMapper;
import com.company.gojob.account_service.model.UserCredential;
import com.company.gojob.account_service.payload.request.UserCreateRequest;
import com.company.gojob.account_service.payload.response.UserCredentialResponse;
import com.company.gojob.account_service.repository.UserCredentialRepository;
import com.company.gojob.account_service.service.implement.IUserCredentialService;
import com.company.gojob.common_service.config.Trans;
import com.company.gojob.common_service.payload.response.BaseResponse;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserCredentialService implements IUserCredentialService {
    @Autowired
    UserCredentialRepository userCredentialRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<UserCredential> getAllUserCredentials() {
        List<UserCredential> listUser = userCredentialRepository.findAllUserCredentials();
        if (listUser.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return listUser;
    }

    @Override
    public ResponseEntity<?> registerUser(UserCreateRequest userCreateRequest) {
        boolean isUserExist = getUserCredentialByUserNameOrEmail(userCreateRequest.getUsername(), userCreateRequest.getEmail());
        if (isUserExist) {
            return BaseResponse.error("Username or Email is already in use.");
        }
        UserCredential userEntity = UserCredentialMapper.INSTANCE.toEntity(userCreateRequest);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setStatus("ACTIVE");
        userEntity.setRole("USER");
        var isSuccess = userCredentialRepository.save(userEntity);
        return BaseResponse.success(Trans.getMessage("success.response-message"), isSuccess);
    }

    @Override
    public Page<UserCredential> findAllUserCredentials(Pageable pageable) {
        Page<UserCredential> paginateUserResponse = userCredentialRepository.findAllUserCredentials(pageable);
        if (paginateUserResponse.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return paginateUserResponse;
    }

    @Override
    public Page<UserCredential> getAllUserCredentialSearch(Pageable pageable, String typeSearch, String valueSearch) {
        Page<UserCredential> listUser = userCredentialRepository.getAllUserCredentialSearch(pageable, typeSearch, valueSearch);
        if (listUser.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return listUser;
    }

    @Override
    public UserCredential getUserCredentialById(String id) {
        UserCredential user = userCredentialRepository.findUserCredentialById(id);
        if (Objects.isNull(user)) {
            throw new EntityNotFoundException();
        }
        return user;
    }

    @Override
    public UserCredentialResponse getUserCredentialByUserName(String username) {
        UserCredential userCredential = userCredentialRepository.findUserCredentialByUsername(username);
        if (userCredential == null) {
            return null;
        }
        return modelMapper.map(userCredential, UserCredentialResponse.class);
    }

    @Override
    public boolean getUserCredentialByUserNameOrEmailOrPhone(String username, String email, String phoneNumber) {
        UserCredential checkUserExist = userCredentialRepository.findUserCredentialByUsernameOrEmailOrPhoneNumber(username, email, phoneNumber);
        return Objects.nonNull(checkUserExist);
    }

    @Override
    public boolean getUserCredentialByUserNameOrEmail(String username, String email) {
        UserCredential checkUserExist = userCredentialRepository.findUserCredentialByUsernameOrEmail(username, email);
        return Objects.nonNull(checkUserExist);
    }

    @Override
    public int updateUserCredentialById(String id, String email, String username) {
        UserCredential checkUserExist = userCredentialRepository.findUserCredentialById(id);
        if (Objects.isNull(checkUserExist)) {
            throw new EntityNotFoundException();
        }

        int isSuccess = userCredentialRepository.updateUserCredentialById(id, email, username);
        if (isSuccess > 0) {
            return 1;
        }
        return 0;
    }

    @Override
    public int deleteUserCredentialById(String id) {
        UserCredential checkUserExist = userCredentialRepository.findUserCredentialById(id);
        if (Objects.isNull(checkUserExist)) {
            throw new EntityNotFoundException();
        }

        int isSuccess = userCredentialRepository.deleteUserCredentialById(id);
        if (isSuccess > 0) {
            return 1;
        }
        return 0;
    }

    @Override
    public UserCredential saveEntity(UserCredential userEntity) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

        userEntity.setStatus("ACTIVE");
        userEntity.setRole("USER");
        userCredentialRepository.save(userEntity);
        return userEntity;
    }
}
