package com.citiustech.pms.patient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.citiustech.pms.patient.entity.Patient;
import com.citiustech.pms.patient.exception.IncorrectEmailAddressException;
import com.citiustech.pms.patient.model.PatientForm;
import com.citiustech.pms.patient.model.PatientVisit;
import com.citiustech.pms.patient.service.PatientService;

@RestController
@CrossOrigin(origins = "*")
public class PatientController {

	@Autowired
	PatientService patientService;

	@RequestMapping(method = RequestMethod.POST, path = "/PMS/pmsDetails/patientDetails", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Patient createPatient(@RequestBody PatientForm patient) throws IncorrectEmailAddressException {
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
}
