package com.naveen.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naveen.bindings.LoginForm;
import com.naveen.bindings.UnlockAccForm;
import com.naveen.bindings.UserRegForm;
import com.naveen.entity.CityMasterEntity;
import com.naveen.entity.CountryMasterEntity;
import com.naveen.entity.StateMasterEntity;
import com.naveen.entity.UserDtlsEntity;
import com.naveen.repository.CityMasterRepo;
import com.naveen.repository.CountryMasterRepo;
import com.naveen.repository.StateMasterRepo;
import com.naveen.repository.UserDtlsRepo;
import com.naveen.util.EmailUtils;

@Service
public class UserMgmtServiceImpl implements UserMgmtService {

	@Autowired
	private UserDtlsRepo userDtlsRepo;
	
	@Autowired
	private CountryMasterRepo countryRepo;
	
	@Autowired
	private StateMasterRepo stateRepo;
	
	@Autowired
	private CityMasterRepo cityRepo;
	
	@Autowired
	private EmailUtils emailUtils;
	
	@Override
	public String loginUser(LoginForm login) {
		UserDtlsEntity userData = userDtlsRepo.findByEmailAndPassword(login.getEmail(), login.getPassword());
		if(userData==null) {
			return "invalid Credentials"; 
		}if(userData!=null && userData.equals("Locked")) {
			return "Account Locked";
		}
		
		return "Login Successfull";
	}

	@Override
	public String emailValidation(String email) {
		UserDtlsEntity userData = userDtlsRepo.findByEmail(email);
		if(userData==null) {
			return "UNIQUE";
		}
		return "Duplicate";
	}

	@Override
	public Map<Integer, String> getCountry() {
		List<CountryMasterEntity> countries = countryRepo.findAll();
		Map<Integer, String> countryMap =new HashMap<>();
		for(CountryMasterEntity entity :countries) {
		countryMap.put(entity.getCountryId(), entity.getCountryName());
		}
		
		
		return countryMap;
	}

	@Override
	public Map<Integer, String> getState(int countryId) {
		
		List<StateMasterEntity> states = stateRepo.findByCountryId(countryId);
		Map<Integer, String> stateMap =new HashMap<>();
		for(StateMasterEntity entity :states) {
			stateMap.put(entity.getCountryId(), entity.getStateName());
		}
		return stateMap;
	}

	@Override
	public Map<Integer, String> getCity(int stateId) {
		
		List<CityMasterEntity> cities = cityRepo.findByCityId(stateId);
		Map<Integer, String> cityMap =new HashMap<>();
		for(CityMasterEntity entity :cities) {
			cityMap.put(entity.getStateId(), entity.getCityName());
		}
		return cityMap;
	}
	@Override
	public String registerUser(UserRegForm user) {
		
		UserDtlsEntity entity =new UserDtlsEntity();
		
		BeanUtils.copyProperties(user, entity);
		
		entity.setAccStatus("Locked");
		entity.setPassword(generateRandomPassword());
	
		UserDtlsEntity savedData = userDtlsRepo.save(entity);
		
		String email =user.getEmail();
		String subject="User Registration process";
		String fileName ="EmailBodyContent.txt";
		String readMailBodyData = readMailBodyData(fileName, entity);
		
		boolean emailSent = emailUtils.emailSent(email, subject, readMailBodyData);
		if(savedData!=null && emailSent) {
			return "Successfully Register";
		}
		return "Failed Registration";
	}
	
	private String readMailBodyData(String fileName, UserDtlsEntity entity) {
		String mailBody =null;
		try {
			StringBuffer sb =new StringBuffer();
		FileReader fr =new FileReader(fileName);
		BufferedReader br = new BufferedReader(fr);
		String ReadLine = br.readLine();
		while(ReadLine!=null) {
			sb.append(ReadLine);
			ReadLine = br.readLine();
		}
		mailBody = sb.toString();
		mailBody.replace("{FNAME}", entity.getfName());
		mailBody.replace("{LNAME}", entity.getlName());
		mailBody.replace("{EMAIL}", entity.getEmail());
		mailBody.replace("{TEMP-PWD}", entity.getPassword());
		mailBody.replace("{PWD}", entity.getPassword());
		
		br.close();
		}catch (Exception e) {
			e.getStackTrace();
		}
		
		return mailBody;
		
	}
	private static String generateRandomPassword()
    {
		int len=6;
        // ASCII range – alphanumeric (0-9, a-z, A-Z)
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
 
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
 
        // each iteration of the loop randomly chooses a character from the given
        // ASCII range and appends it to the `StringBuilder` instance
 
        for (int i = 0; i < len; i++)
        {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }
 
        return sb.toString();
    }

	@Override
	public String unlockAcc(UnlockAccForm unlock) {
		if(!(unlock.getNewPwd().equals(unlock.getConfirmNewPwd()))) {
			return "New password and Confirm password should be same";
		}
		UserDtlsEntity userData = userDtlsRepo.findByEmailAndPassword(unlock.getEmail(), unlock.getTempPwd());
		if(userData==null) {
			return "Invalid Details";
		}
		userData.setPassword(unlock.getConfirmNewPwd());
		userData.setAccStatus("Unlocked");
		userDtlsRepo.save(userData);
		return "Account Unlocked";
	}

	

	@Override
	public String forgotPassword(String email) {
		UserDtlsEntity userEmail = userDtlsRepo.findByEmail(email);
		
		if(userEmail==null) {
			return "Invalid Email";
		}
		String fileName="UnlockAccDetails.txt";
		String subject ="forgot password details";
		String readMailBodyData = readMailBodyData(fileName, userEmail);
		boolean emailSent = emailUtils.emailSent(fileName, subject, readMailBodyData);
		if(userEmail!=null && emailSent) {
			return "Sucessfully sent to email";
		}
		return "Error";
	}

	

}
