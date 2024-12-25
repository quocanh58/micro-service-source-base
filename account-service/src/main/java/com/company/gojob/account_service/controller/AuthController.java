package com.company.gojob.account_service.controller;

import com.company.gojob.account_service.constant.ApiEndpoints;
import com.company.gojob.account_service.mapper.UserCredentialMapper;
import com.company.gojob.account_service.model.UserCredential;
import com.company.gojob.account_service.payload.request.AuthRequest;
import com.company.gojob.account_service.payload.request.UpdateUserRequest;
import com.company.gojob.account_service.payload.request.UserCreateRequest;
import com.company.gojob.account_service.payload.response.LoginResponse;
import com.company.gojob.account_service.service.AuthService;
import com.company.gojob.account_service.service.JwtService;
import com.company.gojob.account_service.service.UserCredentialService;
import com.company.gojob.common_service.config.Trans;
import com.company.gojob.common_service.payload.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin("*")
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JwtService jwtService;

    @Autowired
    UserCredentialService userCredentialService;

    @PostMapping(ApiEndpoints.AUTH_REGISTER)
    @Operation(summary = "Register API", description = "Register account to authentication")
    public ResponseEntity<?> addNewUser(@RequestBody @Valid UserCreateRequest userCreateRequest,
                                        @RequestHeader(value = "Accept-Language", defaultValue = "en") String language) {
        return userCredentialService.registerUser(userCreateRequest);
    }

    @PostMapping(ApiEndpoints.AUTH_GENERATE_TOKEN)
    public String getToken(@RequestBody AuthRequest request) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        if (authentication.isAuthenticated()) {
            return authService.generateToken(request.getUsername());
        }
        throw new RuntimeException("Authentication is not authenticated.");
    }

    @PostMapping(ApiEndpoints.AUTH_LOGIN)
    @Operation(summary = "Login API", description = "Authenticate user and return access and refresh tokens")
    @ApiResponse(responseCode = "200", description = "Successful login")
    @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid credentials")
    @ApiResponse(responseCode = "403", description = "Forbidden - Access denied")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest request,
                                   @RequestHeader(value = "Accept-Language", defaultValue = "en") String language) {
        return authService.authenticateUser(request);
    }

    @PostMapping(ApiEndpoints.AUTH_REFRESH_TOKEN)
    public ResponseEntity<?> refreshToken(@RequestParam String tokenRequest) {
        return jwtService.refreshTokens(tokenRequest);
    }


    @GetMapping(ApiEndpoints.AUTH_VALIDATE_TOKEN)
    public ResponseEntity<?> validateToken(@RequestParam("token") String token,
                                           @RequestHeader(value = "Accept-Language", defaultValue = "en") String language) {
        authService.invalidateToken(token);
        return BaseResponse.success(Trans.getMessage("success.response-message"), "Token is valid.");
    }


    @GetMapping(ApiEndpoints.GET_ALL_USERS)
    public ResponseEntity<?> getAllUsers(@RequestHeader(value = "Accept-Language", defaultValue = "en") String language) {
        return BaseResponse.success(Trans.getMessage("success.response-message"), userCredentialService.getAllUserCredentials());
    }

    @GetMapping(ApiEndpoints.GET_ALL_USERS_SEARCH)
    public ResponseEntity<?> getAllUsers(@RequestParam(value = "page", defaultValue = "1") int page,
                                         @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                         @RequestParam(value = "typeSearch") String typeSearch,
                                         @RequestParam(value = "valueSearch", defaultValue = "") String valueSearch,
                                         @RequestParam(value = "sortBy", defaultValue = "") String sortBy,
                                         @RequestParam(value = "sortDirection", defaultValue = "desc") String sortDirection,
                                         @RequestHeader(value = "Accept-Language", defaultValue = "en") String language
    ) {
        Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page - 1, pageSize, sort);
        return BaseResponse.success(Trans.getMessage("success.response-message"), userCredentialService.getAllUserCredentialSearch(pageable, typeSearch, valueSearch), null);
    }


    @GetMapping(ApiEndpoints.GET_ALL_USERS_PAGINATE)
    public ResponseEntity<?> getAllUsersPaginate(@RequestParam(value = "page", defaultValue = "1") int page,
                                                 @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                 @RequestHeader(value = "Accept-Language", defaultValue = "en") String language) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        return BaseResponse.success(Trans.getMessage("success.response-message"), userCredentialService.findAllUserCredentials(pageable));
    }

    @GetMapping(ApiEndpoints.GET_USER_BY_ID)
    public ResponseEntity<?> getUserById(@PathVariable String id,
                                         @RequestHeader(value = "Accept-Language", defaultValue = "en") String language) {
        return BaseResponse.success(Trans.getMessage("success.response-message"), userCredentialService.getUserCredentialById(id));
    }

    @PutMapping(ApiEndpoints.UPDATE_USER)
    public ResponseEntity<?> updateUser(@PathVariable String id, @Valid @RequestBody UpdateUserRequest request,
                                        @RequestHeader(value = "Accept-Language", defaultValue = "en") String language) {
        int isSuccess = userCredentialService.updateUserCredentialById(id, request.getEmail(), request.getUsername());
        if (isSuccess < 1) {
            return BaseResponse.error();
        }
        return BaseResponse.success(Trans.getMessage("success.response-message"), userCredentialService.getUserCredentialById(id));
    }

    @DeleteMapping(ApiEndpoints.DELETE_USER)
    public ResponseEntity<?> deleteUser(@PathVariable String id,
                                        @RequestHeader(value = "Accept-Language", defaultValue = "en") String language) {
        int isSuccess = userCredentialService.deleteUserCredentialById(id);
        if (isSuccess < 1) {
            return BaseResponse.error();
        }
        return BaseResponse.success(Trans.getMessage("success.response-message"), userCredentialService.getUserCredentialById(id));
    }

}
