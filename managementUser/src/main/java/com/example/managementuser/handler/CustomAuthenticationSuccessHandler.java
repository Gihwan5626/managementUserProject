package com.example.managementuser.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,  HttpServletResponse response, Authentication authentication) 
			throws IOException, ServletException {

			// 예: 로그인 성공 후 다른 페이지로 리다이렉트
			response.sendRedirect("/");
			
			// 또는 사용자 정보를 활용하여 추가적인 처리 로직을 실행할 수 있습니다.
			 String username = authentication.getName();
			 System.out.println("User " + username + " has logged in successfully");
			}
}
