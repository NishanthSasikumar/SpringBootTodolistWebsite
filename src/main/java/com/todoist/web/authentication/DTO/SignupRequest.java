package com.todoist.web.authentication.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;



@Getter
@Setter
@AllArgsConstructor
public class SignupRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String name;

    @NotBlank
    private String password;

    @NotBlank
    private String confirmPassword;
}
