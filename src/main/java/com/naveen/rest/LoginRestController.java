package com.naveen.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.naveen.bindings.LoginForm;
import com.naveen.service.UserMgmtService;

@RestController
@RequestMapping("/login")
public class LoginRestController {
	
	@Autowired
	private UserMgmtService userMgmtService;
	
	@GetMapping("/user")
	public String loginUser(@RequestBody LoginForm user){
		
		String loginStatus = userMgmtService.loginUser(user);
		
		return loginStatus;
	}
}
