package com.example.managementuser.service;

import com.example.managementuser.entity.UserHistoryEntity;
import com.example.managementuser.type.ActionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.managementuser.dto.JoinDTO;
import com.example.managementuser.entity.UserEntity;
import com.example.managementuser.repository.UserHistoryRepository;
import com.example.managementuser.repository.UserRepository;

@Service
public class JoinService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserHistoryRepository userHistoryRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	public String idCheck(JoinDTO joinDTO) {
		
		boolean isUser =  userRepository.existsByUsername(joinDTO.getUsername());
		if 	(isUser) {
			return "EXIST";
		}
		return "NOT_EXIST";
	}
		
		
	public void joinProcess(JoinDTO joinDTO, UserHistoryEntity userHistoryEntity) {
		
		boolean isUser =  userRepository.existsByUsername(joinDTO.getUsername());
		if 	(isUser) {
			return;
		}
		
		UserEntity data = new UserEntity();
		
		data.setUsername(joinDTO.getUsername());
		data.setPassword(bCryptPasswordEncoder.encode(joinDTO.getPassword()));
		data.setName(joinDTO.getName());
		data.setRoll("SYSTEM_USER");

		userRepository.save(data);
		
		userHistoryEntity.setRegUserIdx(data.getId());
		userHistoryEntity.setActionType(ActionType.C);
		
		userHistoryRepository.save(userHistoryEntity);
	}
	
}

