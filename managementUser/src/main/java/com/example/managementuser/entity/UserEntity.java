package com.example.managementuser.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@ToString
@Table(name = "system_user")
public class UserEntity {

	@Id
	@Column(name = "user_idx")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "user_id", unique = true)
	private String username;
	
	@Column(name = "user_pw")
	private String password;

	@Column(name = "user_nm")
	private String name;
	
	@Column(name = "user_auth")
	private String roll;
	
	
}
