package com.todoist.web.authentication.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@AllArgsConstructor
@NoArgsConstructor
@Component
public class JWTFilter extends OncePerRequestFilter {

    JWTService jwtService;
    CustomUserDetailService customUserDetailService;
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String jwtString = null;
        Cookie[] cookie = request.getCookies();
        if(cookie!=null){
            for(Cookie c:cookie){
                if("jwt".equals(c.getName())){
                    jwtString=c.getValue();
                    break;
                }
            }
        }

        if(jwtString!=null && SecurityContextHolder.getContext().getAuthentication()==null){

            SecurityContext context = SecurityContextHolder.createEmptyContext();
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    customUserDetailService.loadUserByUsername(jwtService.getEmail(jwtString)),)
        }
        filterChain.doFilter(request,response);
    }
}
