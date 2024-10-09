package com.example.managementuser.service;

import com.example.managementuser.dto.searchDTO;
import com.example.managementuser.entity.UserEntity;
import com.example.managementuser.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class UserManagementApiService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	public List<UserEntity> userSearchList(searchDTO search) {

		List<UserEntity> list = new ArrayList<>();

		if(search.getUserId() != null)
			list = userRepository.findByUsernameContaining(search.getUserId());
		if(search.getUserNm() != null)
			list.addAll(userRepository.findByNameContaining(search.getUserNm()));
		if((search.getUserId() == null) && (search.getUserNm() == null))
			list.addAll(userRepository.findAll());

		HashSet<UserEntity> distinctData = new HashSet<UserEntity>(list);
		list = new ArrayList<UserEntity>(distinctData);

		return list;
	}
	
	public void userInsert(UserEntity userEntity) {

		userEntity.setPassword(bCryptPasswordEncoder.encode(userEntity.getPassword()));

		userRepository.save(userEntity);
		return ;
	}
	
	
	
	public String userUpate(searchDTO search) {
		
		UserEntity preuser = userRepository.findByUsername(search.getUserId());
		String preName;
		
		if( preuser == null) {
			return "일치하는 아이디가 없습니다.";
		}else {
			preName = preuser.getName();
			preuser.setName(search.getUserNm());
		}
		userRepository.save(preuser);
		
		return preName + " -> " + preuser.getName() + " 로 변경이 완료됐습니다.";
	}
	
	public String userDelete(String userId) {
		
		UserEntity preuser = userRepository.findByUsername(userId);
		String preName;
		
		if( preuser == null) {
			return "일치하는 아이디가 없습니다.";
		}
		userRepository.delete(preuser);
		
		return preuser.toString() +  " 데이터가 삭제되었습니다.";
	}
	
}
