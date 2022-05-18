package com.naveen.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.naveen.bindings.UnlockAccForm;
import com.naveen.service.UserMgmtService;

@RestController
public class UnlockAccRestController {
	
	@Autowired
	private UserMgmtService userMgmtService;
	
	@PostMapping("/unlockAcc")
	public String unlockAccount(@RequestBody UnlockAccForm unlock){
		String accUnlock = userMgmtService.unlockAcc(unlock);
		return accUnlock;
	}

}
