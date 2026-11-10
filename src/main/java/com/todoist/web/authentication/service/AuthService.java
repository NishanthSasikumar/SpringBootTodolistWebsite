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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.todoist.web.authentication.repository.AuthRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {

    AuthRepository authRepository;
    RoleRepository roleRepository;
    AuthMapper authMapper;
    JWTService jwtService;
    AuthenticationManager authenticationManager;
    public AuthService(
            AuthRepository authRepository,
            AuthMapper authMapper,
            RoleRepository roleRepository,
            JWTService jwtService,
            AuthenticationManager authenticationManager
    ){
        this.jwtService=jwtService;
        this.roleRepository=roleRepository;
        this.authRepository=authRepository;
        this.authMapper = authMapper;
        this.authenticationManager = authenticationManager;
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
        String jwtToken = jwtService.generateToken(signupVariable.getEmail(),user.getRole().getRole());
        Cookie cookie = new Cookie("jwt",jwtToken);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60*30);
        cookie.setSecure(false);

        response.addCookie(cookie);

        AuthResponse authResponse = new AuthResponse(role.getRole(),signupVariable.getEmail(),signupVariable.getName(),"Signup successfully");
        return authResponse;
    }

    public AuthResponse login(LoginRequest loginRequest,HttpServletResponse response){
//        if(!authRepository.existsByEmail(loginRequest.getEmail())){
//            throw new UserNotFoundException("Invalid email or password");
//        }
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        if(encoder.matches(user.getPassword(), loginRequest.getPassword())){
//            throw new InvalidPassword("Invalid email or password");
//        }
//        if(user.getBlocked()==true){
//            throw new BlockedUser("User account is blocked");
//        }

        Authentication authentication;
        User user = authRepository.findByEmail(loginRequest.getEmail());

        try{
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),loginRequest.getPassword()
            ));
        } catch (DisabledException ex) {
            throw new BlockedUser("User account is blocked");
        } catch (AuthenticationException ex){
            throw new InvalidCredentialsException("Invalid email or password");
        }

        String jwtToken = jwtService.generateToken(loginRequest.getEmail(),user.getRole().getRole());
        Cookie cookie = new Cookie("jwt",jwtToken);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60*30);
        cookie.setSecure(false);
        response.addCookie(cookie);
        AuthResponse authResponse = new AuthResponse(
                user.getRole().getRole(),
                loginRequest.getEmail(),
                user.getName(),
                "Login successfully");
        return authResponse;
    }
}
