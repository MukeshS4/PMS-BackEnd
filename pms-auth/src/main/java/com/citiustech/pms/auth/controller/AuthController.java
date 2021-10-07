package com.citiustech.pms.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.citiustech.pms.auth.exception.IdenticalPasswordException;
import com.citiustech.pms.auth.exception.PasswordMismatchException;
import com.citiustech.pms.auth.model.ChangePassword;
import com.citiustech.pms.auth.service.AuthService;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class AuthController {
	
//	 private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	@Autowired
	AuthService authService;

	//@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value = "/PMS/pmsPassword/changePassword", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> changePassword(@RequestBody ChangePassword passwordData) throws IdenticalPasswordException, PasswordMismatchException {
		return ResponseEntity.ok(authService.updatePassword(passwordData));
	}
	
	@RequestMapping(value = "/PMS/pmsLock/lockAccount", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> lockAccount(@RequestBody String username,@RequestBody int type) {
		System.out.println("hit Lock");
		System.out.println(username+" "+type);
		return ResponseEntity.ok(authService.lockAccount(username));
	}
	
	@RequestMapping(value = "/PMS/pmsLock/unLockAccount", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> unLockAccount(@RequestBody String username) {
		System.out.println("hit Lock");
		return ResponseEntity.ok(authService.unLockAccount(username));
	}
	
	@RequestMapping(value = "/PMS/pmsStatus/deactivateAccount", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> deactivateAccount(@RequestBody String emailId) {
		return ResponseEntity.ok(authService.deActivateAccount(emailId));
	}
	
	@RequestMapping(value = "/PMS/pmsStatus/activateAccount", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> activateAccount(@RequestBody String emailId) {
		return ResponseEntity.ok(authService.activateAccount(emailId));
	}

	@RequestMapping(value = "/PMS/pmsGet/getAllUsers", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getAllUsers() throws Exception {
		return ResponseEntity.ok(authService.getAllUsers());
	}
	
	@RequestMapping(value = "/PMS/pmsGet/getAllBockedUsers", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getAllBlockedUsers() throws Exception {
		return ResponseEntity.ok(authService.getAllBlockedUsers());
	}
	
	@RequestMapping(value = "/PMS/pmsGet/getAllPatients", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getAllPatients() throws Exception {
		return ResponseEntity.ok(authService.getAllPatients());
	}
	
	@RequestMapping(value = "/PMS/pmsGet/getAllBockedPatients", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getAllBlockedPatients() throws Exception {
		return ResponseEntity.ok(authService.getAllBlockedPatients());
	}
	
	@RequestMapping(value = "/PMS/pmsDelete/deleteUser", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> deleteUser(@RequestBody String emailId) throws Exception {
		return ResponseEntity.ok(authService.deleteUser(emailId));
	}
	
	@RequestMapping(value = "/PMS/pmsGet/getEmployeeId", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getUniqueEmployeeId() {
		System.out.println("Hi");
		return ResponseEntity.ok(authService.getUniqueEmployeeId());
	}
	
	@RequestMapping(value = "/PMS/pmsPassword/forgotPassword", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> forgotPassword(@RequestBody String emailId) {
		return ResponseEntity.ok(authService.deleteUser(emailId));
	}
	
}
