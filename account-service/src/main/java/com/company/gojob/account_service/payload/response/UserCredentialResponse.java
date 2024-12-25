package com.company.gojob.account_service.payload.response;

import com.company.gojob.account_service.model.UserCredential;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCredentialResponse {
    private String id;
    private String username;
    private String email;
    @JsonIgnore
    private String password;
}
