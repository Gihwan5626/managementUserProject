package com.example.managementuser.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.managementuser.handler.CustomAuthenticationFailureHandler;
import com.example.managementuser.handler.CustomAuthenticationSuccessHandler;



@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	public CustomAuthenticationFailureHandler customAuthenticationFailureHandler () {
	        return new CustomAuthenticationFailureHandler();  // 핸들러 빈 등록
	}
	
	@Bean
	public CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler() {
	        return new CustomAuthenticationSuccessHandler();  // 핸들러 빈 등록
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http
		        .authorizeHttpRequests((auth) -> auth
						.requestMatchers("/v3/**", "/swagger-ui/**","/swagger", "/swagger-ui.html"
								, "/api-docs", "/api-docs/**").permitAll()
		                .requestMatchers("/login", "/loginProc", "/join", "/joinProc", "/join/**", "/css/**", "/management/**").permitAll()
		                .requestMatchers("/admin", "/user-history/**").hasAuthority("SYSTEM_ADMIN")
		                .requestMatchers("/my/**", "/main").hasAnyAuthority("SYSTEM_ADMIN", "SYSTEM_USER")
		                .anyRequest().authenticated()
                );


        http
                .formLogin((auth) -> auth.loginPage("/login")
                        .loginProcessingUrl("/loginProc")
                        .successHandler(customAuthenticationSuccessHandler()) 
                        .failureHandler(customAuthenticationFailureHandler())
                        .permitAll()
                );

        http
                .csrf((auth) -> auth.disable());

        http
        		.sessionManagement((auth) -> auth
        		.maximumSessions(1)
        		.maxSessionsPreventsLogin(true));
        
        http
        		.sessionManagement((auth) -> auth
        		.sessionFixation().changeSessionId());

        return http.build();
    }
    
    
}