package com.todoist.web.authentication.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginRequest {
    @NotBlank(message = "Email shouldn't be blank")
    @Email
    private String email;

    @NotBlank(message = "Password shouldn't be empty")
    private String password;
}
