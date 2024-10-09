package com.example.managementuser.handler;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {
        
        // 실패 메시지를 로그에 남기거나, 에러 페이지로 리다이렉트할 수 있음
        System.out.println("Login failed: " + exception.getMessage());

        // JSON 응답으로 실패 메시지를 보낼 경우
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        response.setContentType("application/json");
//        response.getWriter().write("{\"error\": \"" + exception.getMessage() + "\"}");
        
        if (exception.getMessage().contains("Bad credentials")) {
            response.getWriter().write("{\"error\": \"Invalid username or password\"}");
        } else if (exception instanceof LockedException) {
            response.getWriter().write("{\"error\": \"Account is locked\"}");
        } else if (exception instanceof DisabledException) {
            response.getWriter().write("{\"error\": \"Account is disabled\"}");
        } else {
            response.getWriter().write("{\"error\": \"Authentication failed\"}");
        }
        
        response.sendRedirect("/login");
    }
}
