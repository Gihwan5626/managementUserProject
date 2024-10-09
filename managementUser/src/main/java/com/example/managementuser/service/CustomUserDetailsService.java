package com.example.managementuser.service;

import com.example.managementuser.dto.CutomUserDetails;
import com.example.managementuser.entity.UserEntity;
import com.example.managementuser.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserEntity userData = userRepository.findByUsername(username);
		
		if (userData != null) {
			
			return new CutomUserDetails(userData);
			
		}
		
		
		return null;
	}
	

}
