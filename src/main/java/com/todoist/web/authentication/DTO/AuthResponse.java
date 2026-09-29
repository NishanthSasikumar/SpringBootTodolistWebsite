package com.todoist.web.authentication.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthResponse {
    private String role;
    private String email;
    private String name;
    private String msg;
}

