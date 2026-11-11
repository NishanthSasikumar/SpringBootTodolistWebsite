package com.todoist.web.resetpassword.service;

import com.todoist.web.authentication.entity.User;
import com.todoist.web.authentication.repository.AuthRepository;
import com.todoist.web.globalException.exceptions.InvalidPassword;
import com.todoist.web.globalException.exceptions.UserNotFoundException;
import com.todoist.web.resetpassword.entity.PasswordReset;
import com.todoist.web.resetpassword.record.ForgetPasswordEvent;
import com.todoist.web.resetpassword.repository.ForgetPasswordRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;

@Service
@AllArgsConstructor
public class UpdatePasswordService {

    AuthRepository authRepository;
    ForgetPasswordRepository forgetPasswordRepository;
    ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public ResponseEntity<String> forgetPassword(String email){
        User user=authRepository.findByEmail(email);
        System.out.println(user);
        if(user==null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        SecureRandom secureRandom=new SecureRandom();
        byte[] randomBytes=new byte[32];
        secureRandom.nextBytes(randomBytes);
        String token = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
        Instant expiry = Instant.now().plusSeconds(900);
        PasswordReset passwordReset=new PasswordReset(user,token,expiry);
        PasswordReset saved=forgetPasswordRepository.save(passwordReset);
        System.out.println("Saved password reset ID: " + saved.getId());
        applicationEventPublisher.publishEvent(new ForgetPasswordEvent(token,email));
        return new ResponseEntity<>("Will we send an email to the registered email", HttpStatus.OK);
    }

    @Transactional
    public void updatePassword(String token,String password){
        if(token==null)
            throw new IllegalArgumentException("Invalid or expired session");

        if(password==null)
            throw new InvalidPassword("Password can't be null");

        PasswordReset resetToken=forgetPasswordRepository
                .findByHashedTokenAndUsedFalse(token)
                .orElseThrow(()-> new IllegalArgumentException("Illegal Argument"));
        System.out.println("User Id"+resetToken.getId());
        System.out.println("RsetToken"+resetToken.getHashedToken()+resetToken.getUsed());
        Instant now =Instant.now();
        int committedOrNot= forgetPasswordRepository.consumeToken(resetToken.getId(),now);
        if(committedOrNot==0)
            throw new IllegalArgumentException("Invalid or expired session");
        User user=resetToken.getUser_id();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
        user.setPassword(encoder.encode(password));
        authRepository.save(user);
    }
}
