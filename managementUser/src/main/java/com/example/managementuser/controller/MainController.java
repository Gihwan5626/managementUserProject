package com.example.managementuser.controller;

import com.example.managementuser.dto.CutomUserDetails;
import com.example.managementuser.entity.NoticeEntity;
import com.example.managementuser.entity.UserHistoryEntity;
import com.example.managementuser.service.NoticeService;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Hidden
@Controller
public class MainController {

	@Autowired
	private NoticeService noticeService;

	@GetMapping("/")
	public String mainPG(@AuthenticationPrincipal CutomUserDetails userDetailsModel, Model model) {
		
		String id = SecurityContextHolder.getContext().getAuthentication().getName();

		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		Iterator<? extends GrantedAuthority> iter = authorities.iterator();
		GrantedAuthority auth = iter.next();
		String role = auth.getAuthority();
		
		model.addAttribute("id", id);
		model.addAttribute("role", role);

		List<NoticeEntity> noticeList = noticeService.getAllNotice();
		model.addAttribute("noticeList", noticeList);

		return "main";
	}
	@PostMapping(value = "/insertNotice", produces = "text/plain;charset=UTF-8")
	public String insertNotice(HttpServletRequest httpServletRequest, NoticeEntity notice) {


		UserHistoryEntity userHistoryEntity = new UserHistoryEntity();
		userHistoryEntity.setUrl(httpServletRequest.getRequestURI());
		userHistoryEntity.setRegIp(httpServletRequest.getRemoteAddr());

		noticeService.insertNotice(notice);
		return "redirect:/";
	}

}
