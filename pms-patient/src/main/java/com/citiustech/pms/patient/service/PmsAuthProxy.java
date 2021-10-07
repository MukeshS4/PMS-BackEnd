package com.citiustech.pms.patient.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(name="pms-auth")
public interface PmsAuthProxy {

	@PostMapping("/patientRegister")
	public Object savePatient(String username,String passowrd);
}
