package com.todoist.web.authentication.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;



@Getter
@Setter
@AllArgsConstructor
public class SignupRequest {

    @NonNull
    private String email;

    @NonNull
    private String name;

    @NonNull
    private String password;

    @NonNull
    private String confirmPassword;
}
