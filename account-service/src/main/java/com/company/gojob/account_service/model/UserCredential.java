package com.company.gojob.account_service.model;

import com.company.gojob.common_service.model.BaseModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_credential")
public class UserCredential extends BaseModel {
    private String username;
    @JsonIgnore
    private String password;
    private String email;
    private String phoneNumber;
    private String address;
    private String role;
}
