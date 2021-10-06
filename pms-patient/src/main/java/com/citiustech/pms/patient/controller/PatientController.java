package com.citiustech.pms.patient.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import com.citiustech.pms.patient.entity.Demographies;
import com.citiustech.pms.patient.entity.Patient;
import com.citiustech.pms.patient.entity.PatientData;
import com.citiustech.pms.patient.model.PatientForm;
import com.citiustech.pms.patient.model.PatientVisit;
import com.citiustech.pms.patient.service.PatientRegistrationService;
import com.citiustech.pms.patient.service.PatientService;
import com.citiustech.pms.patient.service.PmsAuthProxy;

@RestController
@CrossOrigin(origins = "*")
public class PatientController {
	
	@Autowired
	private PmsAuthProxy pmsAuthProxy;

	@Autowired
	PatientService patientService;

	@Autowired
	PatientRegistrationService patientRegistrationService;
	
	@RequestMapping(method = RequestMethod.POST, path = "/PMS/pmsDetails/patientDetails", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Patient createPatient(@RequestBody PatientForm patient) throws Exception {
		return patientService.savePatient(patient);
	}

	@GetMapping(value = "PMS/pmsDetails/patientDetails/{id}", produces = "application/json")
	public Patient fetchPatient(@PathVariable Integer id) {
		
		System.out.println(id);
		return patientService.getPatient(id);
	}

	@RequestMapping(method = RequestMethod.POST, path = "PMS/pmsDetails/patientVisitDetails", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public PatientVisit createPatientVisit(@RequestBody PatientVisit patientvisit) {
		System.out.println(patientvisit);
		return patientService.savePatientVisit(patientvisit);
	}

	@GetMapping(value = "/getAllPatientVisits")
	public String fetchPatient() {
		return patientService.getPatientVisits().toString();
	}

	@GetMapping("/getAllPatients")
	public List<Patient> getAllPatients(){
		return patientService.getAllPatient();
	}
	@GetMapping("/getAllBlockedPatients")
	public List<Demographies> getAllBlockedPatients(){
		return patientService.getAllBlockedPatients();

	}

	@RequestMapping(value = "/PMS/pmsRegister/patientRegister", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> saveUser(@RequestBody PatientData registerData) throws Exception {
		System.out.println("inside register"+ registerData);
		pmsAuthProxy.savePatient(registerData.getEmailId(), registerData.getPassword());
		return ResponseEntity.ok(patientRegistrationService.save(registerData));
	}

}
