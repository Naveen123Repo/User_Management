package com.naveen.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.naveen.bindings.LoginForm;
import com.naveen.bindings.UnlockAccForm;
import com.naveen.bindings.UserRegForm;

@Service
public interface UserMgmtService {

	public String registerUser(UserRegForm user);

	public String emailValidation(String email);

	public Map<Integer, String> getCountry();

	public Map<Integer, String> getState(int countryId);

	public Map<Integer, String> getCity(int stateId);

	public String unlockAcc(UnlockAccForm unlock);

	public String loginUser(LoginForm login);

	public String forgotPassword(String email);

}
