package com.citiustech.pms.auth.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.citiustech.pms.auth.exception.IdenticalPasswordException;
import com.citiustech.pms.auth.exception.PasswordMismatchException;
import com.citiustech.pms.auth.exception.UserNotFoundException;
import com.citiustech.pms.auth.model.ChangePassword;
import com.citiustech.pms.auth.model.LoginDao;
import com.citiustech.pms.auth.model.UserDataDao;
import com.citiustech.pms.auth.repository.LoginRepository;
import com.citiustech.pms.auth.repository.UserDataRepository;

@Service
public class AuthService implements IAuthService {
	
	@Autowired
	private MailService notificationService;

	@Autowired
	LoginRepository loginRepo;

	@Autowired
	UserDataRepository userDataRepo;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override
	public Object updatePassword(ChangePassword passwordData) throws IdenticalPasswordException, PasswordMismatchException {
		LoginDao user = loginRepo.findByUsername(passwordData.getUsername());
		if (!bcryptEncoder.matches(passwordData.getOldPassword(), user.getPassword())) {
			throw new PasswordMismatchException("Old Password doesn't match");
		}
		else if(bcryptEncoder.matches(passwordData.getNewPassword(), user.getPassword())) {
			throw new IdenticalPasswordException("New Password is the same as Old Password");
		}
		else {
			user.setPassword(bcryptEncoder.encode(passwordData.getNewPassword()));
			System.out.println("Password changed successfully");
			return loginRepo.save(user);
		}
	}

	@Override
	public Object lockAccount(String username) {
		UserDataDao userData = userDataRepo.findByEmailId(username);
		if (userData != null) {
			userData.setUnlocked(false);
			return userDataRepo.save(userData);
		} else {
			System.out.println("No data with specified username");
			return null;
		}

	}

	@Override
	public List<UserDataDao> getAllUsers() {
		List<UserDataDao> usersData = userDataRepo.findAll();
		return usersData;
	}

	@Transactional
	@Override
	public boolean deleteUser(String emailId) {
		try {
			userDataRepo.deleteByEmailId(emailId);
			loginRepo.deleteByUserName(emailId);
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	@Override
	public Object deActivateAccount(String emailId) {
		UserDataDao userData = userDataRepo.findByEmailId(emailId);
		if (userData != null) {
			userData.setStatus(false);
			return userDataRepo.save(userData);
		} else {
			System.out.println("No data with specified username");
			return null;
		}
	}

	@Override
	public Object activateAccount(String emailId) {
		UserDataDao userData = userDataRepo.findByEmailId(emailId);
		if (userData != null) {
			userData.setStatus(true);
			return userDataRepo.save(userData);
		} else {
			System.out.println("No data with specified username");
			return null;
		}
	}

	@Override
	public Object getAllBlockedUsers() {
		List<UserDataDao> blockedUsersData = userDataRepo.findAllBlocked();
		System.out.println(blockedUsersData);
		return blockedUsersData;
	}

	@Override
	public Object unLockAccount(String username) {
		UserDataDao userData = userDataRepo.findByEmailId(username);
		if (userData != null) {
			userData.setUnlocked(true);
			return userDataRepo.save(userData);
		} else {
			System.out.println("No data with specified username");
			return null;
		}
	}

	@Override
	public List<String> getUniqueEmployeeId() {
		List<String> employeeId = userDataRepo.extractId();
		List<String> generateEmpId = new ArrayList<String>();
		for (int i = 1; i <= 999; i++) {
			generateEmpId.add("CT-" + String.format("%03d", i));
		}
		List<String> availableUniqueIds = generateEmpId.stream().filter(e -> !employeeId.contains(e))
				.collect(Collectors.toList());
		return availableUniqueIds;
	}

	@Override
	public boolean forgotPassword(String username) throws UserNotFoundException{
		LoginDao loadUser = loginRepo.findByUsername(username);
		if (loadUser == null) {
			throw new UserNotFoundException("User not found with specified username");
		} else {
			loadUser.setPassword(bcryptEncoder.encode("Password123"));
			notificationService.sendEmailPasswordReset(username);
			return true;
		}
	}

	@Override
	public Object getAllPtients() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getAllBlockedPatients() {
		// TODO Auto-generated method stub
		return null;
	}

}
