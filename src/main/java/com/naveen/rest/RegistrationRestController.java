package com.naveen.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.naveen.bindings.UserRegForm;
import com.naveen.service.UserMgmtService;

@RestController
public class RegistrationRestController {
	
	@Autowired
	private UserMgmtService userMgmtService;
	
	@PostMapping("/user")
	public String registration(@RequestBody UserRegForm user){
		String register = userMgmtService.registerUser(user);
		return register;
	}
	
	@GetMapping("/emailCheck/{Email}")
	public String emailCheck(@PathVariable("Email") String email) {
		String emailValidation = userMgmtService.emailValidation(email);
		return emailValidation;
		
	}
	@GetMapping("/countries")
	public Map<Integer, String> loadCountries() {
		
		Map<Integer, String> countries= userMgmtService.getCountry();
		return countries;
		
	}
	@GetMapping("/states/{countryId}")
	public Map<Integer, String> loadStates(@PathVariable("countryId") Integer cId) {
		
		Map<Integer, String> states= userMgmtService.getState(cId);
		return states;
		
	}
	@GetMapping("/cities/{stateId}")
	public Map<Integer, String> loadCities(@PathVariable("stateId") Integer sId) {
		
		Map<Integer, String> states= userMgmtService.getCity(sId);
		return states;
		
	}

}
