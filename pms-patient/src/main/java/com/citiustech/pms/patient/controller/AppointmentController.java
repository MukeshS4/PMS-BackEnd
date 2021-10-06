package com.citiustech.pms.patient.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.citiustech.pms.patient.entity.Appointments;
import com.citiustech.pms.patient.exception.RecordNotFoundException;
import com.citiustech.pms.patient.service.AppointmentService;

@RestController
@CrossOrigin(origins = "*")
public class AppointmentController {

	@Autowired
	AppointmentService service;

	@GetMapping
	public ResponseEntity<List<Appointments>> getAllAppointments() {
		List<Appointments> list = service.getAllAppointments();

		return new ResponseEntity<List<Appointments>>(list, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Appointments> getAppointmentsById(@PathVariable("id") Integer id)
			throws RecordNotFoundException {
		Appointments entity = service.getAppointmentsById(id);

		return new ResponseEntity<Appointments>(entity, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST,path="PMS/pmsDetails/scheduleMeeting", consumes= MediaType.APPLICATION_JSON_VALUE )
	@ResponseBody
	public ResponseEntity<Appointments> createOrUpdateAppointments(@RequestBody Appointments employee)
			throws RecordNotFoundException {
		Appointments updated = service.createOrUpdateAppointments(employee);
		return new ResponseEntity<Appointments>(updated, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public HttpStatus deleteAppointmentsById(@PathVariable("id") Integer id) throws RecordNotFoundException {
		service.deleteAppointmentsById(id);
		return HttpStatus.ACCEPTED;
	}
}
