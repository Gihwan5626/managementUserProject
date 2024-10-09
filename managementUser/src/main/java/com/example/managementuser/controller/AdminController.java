package com.example.managementuser.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.managementuser.dto.CutomUserDetails;
import com.example.managementuser.entity.UserEntity;
import com.example.managementuser.entity.UserHistoryEntity;
import com.example.managementuser.service.AdminService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;


@Controller
public class AdminController {
	
	@Autowired
	AdminService adminService;
	
	
	@GetMapping("/admin")
	public String adminPG(Model model) {
        List<UserEntity> users = adminService.getAllUsers();
        model.addAttribute("users", users);
		return "admin";
	}
	@GetMapping("/insert/notice")
	public String insertNotice(Model model) {
		return "notice";
	}

	
	@GetMapping("/admin/userDTL")
	public String adminDtlPG(@RequestParam(name="id") int id, Model model) {
	    UserEntity user = adminService.getByUserId(id);
	    if (user == null) {
	        model.addAttribute("errorMessage", "User not found");
	        return "errorPage"; 
	    }
	    model.addAttribute("user", user);
	    return "adminDTL";
	}
	
	@PostMapping("admin/updateUser")
	public String updateUser(@AuthenticationPrincipal CutomUserDetails userDetailsModel, HttpServletRequest httpServletRequest, UserEntity userEntity, Model model) {
		
		UserHistoryEntity userHistoryEntity = new UserHistoryEntity();
		userHistoryEntity.setUrl(httpServletRequest.getRequestURI()); 
		userHistoryEntity.setRegIp(httpServletRequest.getRemoteAddr());
		userHistoryEntity.setRegUserIdx(userDetailsModel.getId());
	    adminService.updateUser(userEntity, userHistoryEntity);

	    return "adminDTL";
	}
	
	@DeleteMapping("admin/deleteUser")
	public String deleteUser(@AuthenticationPrincipal CutomUserDetails userDetailsModel, HttpServletRequest httpServletRequest, UserEntity userEntity, Model model) {
		
		UserHistoryEntity userHistoryEntity = new UserHistoryEntity();
		userHistoryEntity.setUrl(httpServletRequest.getRequestURI()); 
		userHistoryEntity.setRegIp(httpServletRequest.getRemoteAddr()); 
		userHistoryEntity.setRegUserIdx(userDetailsModel.getId());
		
	    adminService.deleteUser(userEntity, userHistoryEntity);

	    return "adminDTL";
	}
	
	@GetMapping("/admin/getRoll")
	@ResponseBody
	public String adminGetRoll(@RequestParam(name="id") int id, Model model) {
	    UserEntity user = adminService.getByUserId(id);
	    if (user == null) {
	        model.addAttribute("errorMessage", "User not found");
	        return "errorPage"; 
	    }
	    return user.getRoll();
	}
	
	
}
