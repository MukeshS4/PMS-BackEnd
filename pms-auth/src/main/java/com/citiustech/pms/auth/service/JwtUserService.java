package com.citiustech.pms.auth.service;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.citiustech.pms.auth.model.LoginDao;
import com.citiustech.pms.auth.model.LoginResponse;
import com.citiustech.pms.auth.model.UserData;
import com.citiustech.pms.auth.model.UserDataDao;
import com.citiustech.pms.auth.repository.LoginRepository;
import com.citiustech.pms.auth.repository.UserDataRepository;

@Service
public class JwtUserService implements UserDetailsService{
	
	@Autowired
	private MailService notificationService;
	
	@Autowired
	private LoginRepository loginRepo;
	
	@Autowired
	private UserDataRepository userRegistrationRepo;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		LoginDao user = loginRepo.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		System.out.println(bcryptEncoder.matches("Ag@stin1407.", (user.getPassword())));
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				new ArrayList<>());
	}
	
	public UserDataDao save(UserData registerData) {
		 UserDataDao newRegister = new  UserDataDao();
		newRegister.setTitle(registerData.getTitle());
		newRegister.setFirstName(registerData.getFirstName());
		newRegister.setLastName(registerData.getLastName());
		newRegister.setEmailId(registerData.getEmailId());
		newRegister.setDateOfBirth(registerData.getDateOfBirth());
		newRegister.setDateofJoining(LocalDate.now());
		newRegister.setRole(registerData.getRole());
		newRegister.setEmployeeId(registerData.getEmployeeId());
		newRegister.setStatus(true);
		newRegister.setUnlocked(true);
		System.out.println("going to mail");
		notificationService.sendEmail(registerData.getEmailId());
		try {
			userRegistrationRepo.save(newRegister);
			LoginDao newLogin = new LoginDao();
			newLogin.setUsername(registerData.getEmailId());
			newLogin.setPassword(bcryptEncoder.encode("Password123"));
			newLogin.setRole(registerData.getRole());
			loginRepo.save(newLogin);
			
			return userRegistrationRepo.save(newRegister);
		}
		catch(Exception E) {
			System.out.println(E);
			return null;
		}
	}

	public LoginResponse getLoginData(String username) {
		UserDataDao regData = userRegistrationRepo.findByEmailId(username);
		if (regData == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		else {
			LoginResponse loginRes = new LoginResponse();
			loginRes.setEmailId(regData.getEmailId());
			loginRes.setFirstName(regData.getFirstName());
			loginRes.setLastName(regData.getLastName());
			loginRes.setRole(regData.getRole());
			loginRes.setName(regData.getFirstName()+regData.getLastName());	
			return loginRes;
		}
	}
}
