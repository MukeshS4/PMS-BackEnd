package com.citiustech.pms.auth.service;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.citiustech.pms.auth.exception.AdminException;
import com.citiustech.pms.auth.exception.IncorrectEmailAddressException;
import com.citiustech.pms.auth.exception.UserAlreadyExistException;
import com.citiustech.pms.auth.exception.UserLockedException;
import com.citiustech.pms.auth.model.LoginDao;
import com.citiustech.pms.auth.model.LoginResponse;
import com.citiustech.pms.auth.model.UserData;
import com.citiustech.pms.auth.model.UserDataDao;
import com.citiustech.pms.auth.repository.LoginRepository;
import com.citiustech.pms.auth.repository.UserDataRepository;

@Service
public class JwtUserService implements UserDetailsService, IJwtUserService {

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
			throw new UsernameNotFoundException("User not found");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				new ArrayList<>());
	}

	@Override
	public UserDataDao save(UserData registerData)
			throws MailException, UserAlreadyExistException, IncorrectEmailAddressException, AdminException {
		boolean exist = userExist(registerData.getEmailId());
		boolean admin = isAdmin(registerData.getRole());
		if (!exist && !admin) {
			UserDataDao newRegister = new UserDataDao();
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
			boolean sendEmail = emailNotification(registerData.getEmailId());
			if (!sendEmail) {
				throw new IncorrectEmailAddressException("Provided Email Id in incorrect or invalid");
			} else {
				try {
					saveLoginDetails(registerData);
					return userRegistrationRepo.save(newRegister);
				} catch (Exception E) {
					System.out.println(E);
					return null;
				}
			}
		} else {
			return null;
		}
	}

	@Override
	public LoginResponse getLoginData(String username) throws UserLockedException {
		UserDataDao regData = userRegistrationRepo.findByEmailId(username);
		if (regData == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		} else {
			if (!regData.isUnlocked()) {
				throw new UserLockedException("User's Account is Locked");
			} else {
				LoginResponse loginRes = new LoginResponse();
				loginRes.setEmailId(regData.getEmailId());
				loginRes.setFirstName(regData.getFirstName());
				loginRes.setLastName(regData.getLastName());
				loginRes.setRole(regData.getRole());
				loginRes.setName(regData.getFirstName() + regData.getLastName());
				return loginRes;
			}
		}
	}

	@Override
	public LoginDao saveLoginDetails(UserData registerData) {
		LoginDao newLogin = new LoginDao();
		newLogin.setUsername(registerData.getEmailId());
		newLogin.setPassword(bcryptEncoder.encode("Password123"));
		newLogin.setRole(registerData.getRole());
		return loginRepo.save(newLogin);
	}

	@Override
	public boolean emailNotification(String email) throws IncorrectEmailAddressException {
		return notificationService.sendEmail(email);
	}

	@Override
	public boolean userExist(String email) throws UserAlreadyExistException {
		UserDataDao isUser = userRegistrationRepo.findByEmailId(email);
		if (isUser != null) {
			throw new UserAlreadyExistException("User is already Registered");
		} else {
			return false;
		}
	}
	
	@Override
	public boolean isAdmin(String role) throws AdminException {
		if(role == "Admin") {
			throw new AdminException("Only a single admin can persist");
		}else {
			return false;
		}
	}
}
