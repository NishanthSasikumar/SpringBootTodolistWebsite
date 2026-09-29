package com.todoist.web.authentication.controller;


import com.todoist.web.authentication.DTO.AuthResponse;
import com.todoist.web.authentication.DTO.LoginRequest;
import com.todoist.web.authentication.DTO.SignupRequest;
import com.todoist.web.authentication.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    AuthService authservice;
    public AuthController(AuthService authservice){
        this.authservice=authservice;
    }

    @PostMapping("/signup")
    public AuthResponse signup(@RequestBody SignupRequest signupRequest, HttpServletResponse response){
        return authservice.signup(signupRequest,response);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest loginRequest,HttpServletResponse response){
        return authservice.login(loginRequest,response);
    }
}
