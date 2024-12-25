package com.company.gojob.account_service.payload.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserCreateRequest {
    @NotBlank(message = "Username cannot be blank")
    @Size(min = 5, max = 20, message = "Username must be between 5 and 20 characters")
    private String username;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
    private String password;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "Email must have character '.' in domain. (Ex: .com / .vn / ... /)"
    )
    private String email;

    @NotBlank(message = "Address cannot be blank")
    @Pattern(
            regexp = "^[a-zA-Z0-9\\s]+$",
            message = "Address cannot contain special characters"
    )
    private String address;

    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "^(0|\\+84)[0-9]{9}$", message = "Phone number must  start with 0 or +84 and be 10 digits long")
    private String phoneNumber;

}
