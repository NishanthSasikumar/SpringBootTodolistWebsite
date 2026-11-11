package com.todoist.web.authentication.controller;


import com.todoist.web.authentication.DTO.AuthResponse;
import com.todoist.web.authentication.DTO.LoginRequest;
import com.todoist.web.authentication.DTO.SignupRequest;
import com.todoist.web.authentication.service.AuthService;
import com.todoist.web.resetpassword.DTOs.forgetpassword.ForgetpasswordRequest;
import com.todoist.web.resetpassword.DTOs.updatepassword.UpdatepasswordRequest;
import com.todoist.web.resetpassword.service.UpdatePasswordService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    AuthService authservice;
    UpdatePasswordService updatePasswordService;
    public AuthController(AuthService authservice,UpdatePasswordService updatePasswordService){
        this.authservice=authservice;
        this.updatePasswordService=updatePasswordService;
    }

    @PostMapping("/signup")
    public AuthResponse signup(@RequestBody SignupRequest signupRequest, HttpServletResponse response){
        return authservice.signup(signupRequest,response);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest loginRequest,HttpServletResponse response){
        return authservice.login(loginRequest,response);
    }

    @PostMapping("/forget-password")
    public ResponseEntity<String> forgetPassword(@RequestBody ForgetpasswordRequest forgetpasswordRequest){
        return updatePasswordService.forgetPassword(forgetpasswordRequest.getEmail());
    }

    @PostMapping("/update-password")
    public ResponseEntity<String> updatePasswrd(@RequestBody UpdatepasswordRequest updatepasswordRequest){
        String password= updatepasswordRequest.getPassword();
        String confirmPassword= updatepasswordRequest.getConfirmPassword();
        if(!password.equals(confirmPassword))
            return new ResponseEntity<>("Password mismatch", HttpStatus.NOT_ACCEPTABLE);
        updatePasswordService.updatePassword(updatepasswordRequest.getToken(),password);
        return new ResponseEntity<>("Password updated successfully",HttpStatus.OK);
    }
}
