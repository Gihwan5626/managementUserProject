package com.example.managementuser.repository;

import com.example.managementuser.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;



public interface UserRepository extends JpaRepository<UserEntity, Integer>{
	
	boolean existsByUsername(String username);
	
	UserEntity findByUsername(String username);
	List<UserEntity> findByUsernameAndName(String username, String name);
	List<UserEntity> findByUsernameContaining(String username);
	List<UserEntity> findByNameContaining(String name);
	List<UserEntity> findAll();
}
