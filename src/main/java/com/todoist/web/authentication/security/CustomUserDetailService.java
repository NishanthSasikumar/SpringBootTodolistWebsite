package com.todoist.web.authentication.security;

import com.todoist.web.authentication.entity.User;
import com.todoist.web.authentication.repository.AuthRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


@Service
@Setter
@Getter
@AllArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    AuthRepository authRepository;

    @Override
    @NullMarked
    public UserDetails loadUserByUsername(String email){
        User user = authRepository.findByEmail(email);
        return org.springframework.security.core.userdetails.User
                .withUsername(email)
                .roles(user.getRole().getRole())
                .build();
    }
}
