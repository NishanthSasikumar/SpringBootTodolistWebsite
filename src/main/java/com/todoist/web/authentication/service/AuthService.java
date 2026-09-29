package com.todoist.web.authentication.service;

import com.todoist.web.authentication.DTO.AuthResponse;
import com.todoist.web.authentication.DTO.LoginRequest;
import com.todoist.web.authentication.DTO.SignupRequest;
import com.todoist.web.authentication.entity.Role;
import com.todoist.web.authentication.entity.User;
import com.todoist.web.authentication.mapper.AuthMapper;
import com.todoist.web.authentication.repository.RoleRepository;
import com.todoist.web.authentication.security.JWTService;
import com.todoist.web.globalException.exceptions.*;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.todoist.web.authentication.repository.AuthRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {

    AuthRepository authRepository;
    RoleRepository roleRepository;
    AuthMapper authMapper;
    JWTService jwtService;
    public AuthService(AuthRepository authRepository,AuthMapper authMapper,RoleRepository roleRepository,JWTService jwtService){
        this.jwtService=jwtService;
        this.roleRepository=roleRepository;
        this.authRepository=authRepository;
        this.authMapper = authMapper;
    }

    public AuthResponse signup(SignupRequest signupVariable, HttpServletResponse response){

        if(authRepository.existsByEmail(signupVariable.getEmail())){
            throw new UserAlreadyExists("User Already Exists");
        }
        if(!signupVariable.getPassword().equals(signupVariable.getConfirmPassword())){
            throw new PasswordMisMatch("Password Mismatching");
        }

        Role role = roleRepository.findByRole("Member").orElseThrow(() -> new RuntimeException("Role Not exist"));

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
        User user = authMapper.toEntity(signupVariable);
        user.setRole(role);
        user.setBlocked(false);
        user.setPassword(encoder.encode(signupVariable.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        authRepository.save(user);
        String jwtToken = jwtService.generateToken(signupVariable.getEmail());
        Cookie cookie = new Cookie("jwt",jwtToken);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60*30);
        cookie.setSecure(false);

        response.addCookie(cookie);

        AuthResponse authResponse = new AuthResponse(role.getRole(),signupVariable.getEmail(),signupVariable.getName(),"Signup successfully");
        return authResponse;
    }

    public AuthResponse login(LoginRequest loginRequest,HttpServletResponse response){
        if(!authRepository.existsByEmail(loginRequest.getEmail())){
            throw new UserNotFoundException("Invalid email or password");
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = authRepository.findByEmail(loginRequest.getEmail());
        if(encoder.matches(user.getPassword(), loginRequest.getPassword())){
            throw new InvalidPassword("Invalid email or password");
        }
        if(user.getBlocked()==true){
            throw new BlockedUser("User account is blocked");
        }

        String jwtToken = jwtService.generateToken(loginRequest.getEmail());
        Cookie cookie = new Cookie("jwt",jwtToken);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60*30);
        cookie.setSecure(false);
        response.addCookie(cookie);
        AuthResponse authResponse = new AuthResponse(user.getRole().getRole(), loginRequest.getEmail(), user.getName(),"Login successfully");
        return authResponse;
    }
}
