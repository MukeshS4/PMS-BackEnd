package com.citiustech.pms.patient.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.citiustech.pms.patient.entity.PatientData;
import com.citiustech.pms.patient.exception.PatientAlreadyExistException;
import com.citiustech.pms.patient.repository.PatientDataRepository;

@Service
public class PatientRegistrationService {
	
	@Autowired
	private PmsAuthProxy pmsAuthProxy;
	
	@Autowired
	private MailService notificationService;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Autowired
	private PatientDataRepository patientRegistrationRepo;
	
	
	public PatientData save(PatientData registerData)
			throws PatientAlreadyExistException {
		boolean exist = patientExist(registerData.getEmailId());
		if (!exist ) {
			PatientData newRegister = new PatientData();
			newRegister.setTitle(registerData.getTitle());
			newRegister.setFirstName(registerData.getFirstName());
			newRegister.setLastName(registerData.getLastName());
			newRegister.setEmailId(registerData.getEmailId());
			newRegister.setDateOfBirth(registerData.getDateOfBirth());
			newRegister.setCountry(registerData.getCountry());
			newRegister.setContactNumber(registerData.getContactNumber());
			newRegister.setPassword(bcryptEncoder.encode(registerData.getPassword()));
			newRegister.setConfirmPassword(bcryptEncoder.encode(registerData.getConfirmPassword()));
			loginPatientData(registerData.getEmailId(),registerData.getPassword());
			boolean email = emailNotification(registerData.getEmailId());
			System.out.println(email);
			return patientRegistrationRepo.save(newRegister);
		}else {
			return null;
		}
	}

	public boolean patientExist(String email) throws PatientAlreadyExistException {
		PatientData isUser = patientRegistrationRepo.findByEmailId(email);
		if (isUser != null) {
			throw new PatientAlreadyExistException("Patient is already Registered");
		} else {
			return false;
		}
	}
	
	public Object loginPatientData(String username,String password) {
		System.out.println("test 1");
		return pmsAuthProxy.savePatient(username, password);
	}
	
	public boolean emailNotification(String email) {
		return notificationService.sendEmail(email);
	}
}
