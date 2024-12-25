package com.company.gojob.account_service.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthRequest {
    @NotBlank(message = "{username.blank}")
    @NotNull(message = "{error.missing-request-parameter}")
    @Size(min = 5, max = 20, message = "{username.size}")
    private String username;

    @NotBlank(message = "{password.blank}")
    @NotNull(message = "{error.missing-request-parameter}")
    @Size(min = 6, max = 100, message = "{password.size}")
    private String password;
}
