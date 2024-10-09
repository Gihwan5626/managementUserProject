package com.example.managementuser.controller;

import com.example.managementuser.dto.JoinDTO;
import com.example.managementuser.entity.UserHistoryEntity;
import com.example.managementuser.service.JoinService;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;



@Hidden
@Controller
public class JoinController {

	@Autowired
	private JoinService joinService;
	
	
	@GetMapping("/join")
	public String joinPG() {
		
		return "join";
		
	}
	
    @GetMapping("/join/idCK.do")
    @ResponseBody
    public Map<String, Object> getAjaxData(JoinDTO joinDTO) {
    	
    	String exist = joinService.idCheck(joinDTO);
    	
//        List<String> items = Arrays.asList("Item 1", "Item 2", "Item 3");
        Map<String, Object> response = new HashMap<>();
        
        response.put("result", exist);
//        response.put("items", items);
        return response;
    }
	
	
	@PostMapping(value = "/joinProc", produces = "text/plain;charset=UTF-8")
	public String joinProcess(HttpServletRequest httpServletRequest, JoinDTO joinDTO) {

		UserHistoryEntity userHistoryEntity = new UserHistoryEntity();
		userHistoryEntity.setUrl(httpServletRequest.getRequestURI()); 
		userHistoryEntity.setRegIp(httpServletRequest.getRemoteAddr()); 
		
		joinService.joinProcess(joinDTO, userHistoryEntity);
		
		return "redirect:/login";
	}
	
}
