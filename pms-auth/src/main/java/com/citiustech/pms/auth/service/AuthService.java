package com.citiustech.pms.auth.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.citiustech.pms.auth.model.ChangePassword;
import com.citiustech.pms.auth.model.LoginDao;
import com.citiustech.pms.auth.model.UserDataDao;
import com.citiustech.pms.auth.repository.LoginRepository;
import com.citiustech.pms.auth.repository.UserDataRepository;

@Service
public class AuthService {

	@Autowired
	LoginRepository loginRepo;
	
	@Autowired
	UserDataRepository userDataRepo;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	public Object updatePassword(ChangePassword passwordData) {
		LoginDao user = loginRepo.findByUsername(passwordData.getUsername());
		if (bcryptEncoder.matches(passwordData.getOldPassword(), user.getPassword())) {
			if (bcryptEncoder.matches(passwordData.getNewPassword(), user.getPassword())) {
				System.out.println("New Password is the same as Old Password");
				return "New Password is the same as Old Password";
			} else {
				user.setPassword(bcryptEncoder.encode(passwordData.getNewPassword()));
				loginRepo.save(user);
				System.out.println("Password changed successfully");
				return "Password changed successfully";
			}
		} else {
			System.out.println("Old Password doesn't match");
			return "Old Password doesn't match";
		}
	}

	public Object lockAccount(String username) {
		UserDataDao userData = userDataRepo.findByEmailId(username);
		if(userData != null) {
			userData.setUnlocked(false);
			return userDataRepo.save(userData);
		}else {
			System.out.println("No data with specified username");
			return null;
		}
		
	}

	public List<UserDataDao> getAllUsers() {
		List<UserDataDao> usersData = userDataRepo.findAll();
		return usersData;
	}

	public Object deleteUser(String emailId) {
		return userDataRepo.deleteByEmailId(emailId);
	}

	public Object deActivateAccount(String emailId) {
		UserDataDao userData = userDataRepo.findByEmailId(emailId);
		if(userData != null) {
			userData.setStatus(false);
			return userDataRepo.save(userData);
		}else {
			System.out.println("No data with specified username");
			return null;
		}
	}

	public Object activateAccount(String emailId) {
		UserDataDao userData = userDataRepo.findByEmailId(emailId);
		if(userData != null) {
			userData.setStatus(true);
			return userDataRepo.save(userData);
		}else {
			System.out.println("No data with specified username");
			return null;
		}
	}

	public Object getAllBlockedUsers() {
		List<UserDataDao> blockedUsersData = userDataRepo.findAllBlocked();
		System.out.println(blockedUsersData);
		return blockedUsersData;
	}

	public Object unLockAccount(String username) {
		UserDataDao userData = userDataRepo.findByEmailId(username);
		if(userData != null) {
			userData.setUnlocked(true);
			return userDataRepo.save(userData);
		}else {
			System.out.println("No data with specified username");
			return null;
		}
	}

}
