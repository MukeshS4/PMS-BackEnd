package com.citiustech.pms.auth.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.citiustech.pms.auth.model.Demographies;

@FeignClient(name="pms-patient")
public interface PmsPatientProxy {
	
	@GetMapping("/getAllPatients")
	public List<Demographies> getAllPatients();
	
	@GetMapping("/getAllBlockedPatients")
	public List<Demographies> getAllBlockedPatients();
	
	

}
