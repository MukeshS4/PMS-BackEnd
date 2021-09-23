package com.citiustech.pms.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.citiustech.pms.auth.config.JwtTokenUtil;
import com.citiustech.pms.auth.model.JwtRequest;
import com.citiustech.pms.auth.model.LoginResponse;
import com.citiustech.pms.auth.model.UserData;
import com.citiustech.pms.auth.service.JwtUserService;

@RestController
@CrossOrigin
public class JwtAuthenticationController {
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserService userService;

	@RequestMapping(value = "/PMS/pmsLogin/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = userService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);
		LoginResponse loginResData = userService.getLoginData(authenticationRequest.getUsername()); 
		loginResData.setAccessToken(token);
		System.out.println(loginResData);
		return ResponseEntity.ok(new LoginResponse(loginResData.getFirstName(),loginResData.getLastName(),loginResData.getEmailId(),loginResData.getName(),loginResData.getRole(),loginResData.getAccessToken()));
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
	
	@RequestMapping(value = "/PMS/pmsRegister/register", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> saveUser(@RequestBody UserData registerData) throws Exception {
		System.out.println("inside register"+ registerData);
		return ResponseEntity.ok(userService.save(registerData));
	}
}
