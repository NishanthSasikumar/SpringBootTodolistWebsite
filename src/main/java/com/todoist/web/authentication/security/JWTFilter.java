package com.todoist.web.authentication.security;

import com.todoist.web.globalException.exceptions.SessionExpiredException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@AllArgsConstructor
@Component
public class JWTFilter extends OncePerRequestFilter {

    JWTService jwtService;
    CustomUserDetailService customUserDetailService;

    @NullMarked
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String jwtString = null;
        Cookie[] cookie = request.getCookies();
        System.out.println("Cookies" + " "+request.getCookies());
        if(cookie!=null){
            for(Cookie c:cookie){
                if("jwt".equals(c.getName())){
                    jwtString=c.getValue();
                    System.out.println("Got Jwt");
                    break;
                }
            }
        }

        if(jwtString!=null && SecurityContextHolder.getContext().getAuthentication()==null){

            SecurityContext context = SecurityContextHolder.createEmptyContext();
            UserDetails userDetails=customUserDetailService.loadUserByUsername(jwtService.getEmail(jwtString));

            if(jwtService.isTokenValid(jwtString)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        userDetails.getPassword(),
                        userDetails.getAuthorities()
                );
                context.setAuthentication(authentication);
                SecurityContextHolder.setContext(context);
                System.out.println(
                        "JWT authentication set: " +
                                SecurityContextHolder.getContext().getAuthentication()
                );
                System.out.println("In SecurityContext");
            }
            else {
                throw new SessionExpiredException("Session Expired");
            }
        }
        filterChain.doFilter(request,response);
    }
}
