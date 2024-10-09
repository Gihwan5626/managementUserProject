package com.example.managementuser.service;

import com.example.managementuser.entity.UserEntity;
import com.example.managementuser.entity.UserHistoryEntity;
import com.example.managementuser.repository.UserHistoryRepository;
import com.example.managementuser.repository.UserRepository;
import com.example.managementuser.type.ActionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserHistoryRepository userHistoryRepository;
	
	public List<UserEntity> getAllUsers() {
		
		List<UserEntity> users =  userRepository.findAll();

		return users;
	}
	
	public UserEntity getByUserId(int searchID) {
		return userRepository.findById(searchID).orElse(null);
		
	}
	
	public void updateUser(UserEntity userEntity, UserHistoryEntity userHistoryEntity) {
		UserEntity preUser = userRepository.findById(userEntity.getId()).orElse(null);
		userEntity.setPassword(preUser.getPassword());
		userRepository.save(userEntity);

		ActionType actionType = preUser != null ? ActionType.U : ActionType.C;
		userHistoryEntity.setActionType(actionType);

		userHistoryRepository.save(userHistoryEntity);
		
		return ;
	}
	
	public void deleteUser(UserEntity userEntity, UserHistoryEntity userHistoryEntity) {
		
		userRepository.delete(userEntity);
		
		userHistoryEntity.setActionType(ActionType.D);
		userHistoryRepository.save(userHistoryEntity);
		return ;
	}
	
}
