package com.naveen.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.naveen.service.UserMgmtService;

@RestController
public class ForgotPwdRestController {

	@Autowired
	private UserMgmtService userMgmtService;
	
	@GetMapping("/email/{email}")
	public String forgotPwd(@PathVariable("email") String email) {
		String forgotPassword = userMgmtService.forgotPassword(email);
		return forgotPassword;
	}
}
