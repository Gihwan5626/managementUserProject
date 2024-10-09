package com.example.managementuser.controller;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.managementuser.dto.CutomUserDetails;
import com.example.managementuser.dto.JoinDTO;
import com.example.managementuser.dto.searchDTO;
import com.example.managementuser.entity.UserEntity;
import com.example.managementuser.service.JoinService;
import com.example.managementuser.service.UserManagementApiService;

import jakarta.servlet.http.HttpServletRequest;


@Tag(name = "외부 API제공 Controller", description = "외부 API Controller입니다.")
@Controller
@RequestMapping(path="/management")
public class UserManagementApiController {

	@Autowired
	UserManagementApiService userManagementApiService;
	
	@GetMapping("/user-list")
	@Operation(summary = "회원조회", description = "회원을 조회하는 API입니다.")
	@Parameters({
			@Parameter(name = "userId", description = "User ID 입력_유사한 ID검색", required = false),
			@Parameter(name = "userNm", description = "User 이름 입력_유사한 이름검색", required = false)
	})
	@ResponseBody
	public List<UserEntity> searchUserList(searchDTO search){
	      return userManagementApiService.userSearchList(search);
	}

	@PostMapping("/insert")
	@Operation(summary = "회원추가", description = "회원을 추가하는 API입니다.")
	@Parameters({
			@Parameter(name = "id", description = "아이디", required = true),
			@Parameter(name = "password", description = "비밀번호", required = true),
			@Parameter(name = "name", description = "이름", required = true),
			@Parameter(name = "role", description = "권한", required = true)
	})
	@ResponseBody
	public String insertUser(@RequestParam Map<String, String> params){
		UserEntity userEntity = new UserEntity();
		userEntity.setUsername(params.get("id"));
		userEntity.setPassword(params.get("password"));
		userEntity.setName(params.get("name"));
		userEntity.setRoll(params.get("role"));
		
		userManagementApiService.userInsert(userEntity);
		String result = userEntity.toString() + "추가 성공";
		
		return result;

	}

	@PutMapping("/update")
	@Operation(summary = "회원수정", description = "회원 아이디를 기준으로 회원 정보 수정하는 API입니다.")
	@Parameters({
			@Parameter(name = "id", description = "아이디", required = true),
			@Parameter(name = "name", description = "이름", required = true)
	})
	@ResponseBody
	public String updateUser(@RequestParam Map<String, String> params){
		
		searchDTO search = new searchDTO();
		search.setUserId(params.get("id"));
		search.setUserNm(params.get("name"));

		return userManagementApiService.userUpate(search);
	}

	@DeleteMapping("/delete")
	@Operation(summary = "회원삭제", description = "회원 아이디를 기준으로 회원 정보 삭제하는 API입니다.")
	@Parameters({
			@Parameter(name = "id", description = "아이디", required = true)
	})
	@ResponseBody
	public String updateUser(@RequestParam(name="id") String userId){

		return userManagementApiService.userDelete(userId);
	}

}
