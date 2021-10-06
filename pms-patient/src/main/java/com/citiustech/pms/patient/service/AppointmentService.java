package com.citiustech.pms.patient.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.citiustech.pms.patient.entity.Appointments;
import com.citiustech.pms.patient.exception.RecordNotFoundException;
import com.citiustech.pms.patient.repository.AppointmentsRepository;

@Service
public class AppointmentService {

	@Autowired
	AppointmentsRepository repository;

	public List<Appointments> getAllAppointments() {
		// TODO Auto-generated method stub
		List<Appointments> employeeList = repository.findAll();

		if (employeeList.size() > 0) {
			return employeeList;
		} else {
			return new ArrayList<Appointments>();
		}
	}

	public Appointments getAppointmentsById(Integer id) throws RecordNotFoundException {
		// TODO Auto-generated method stub
		Optional<Appointments> employee = repository.findById(id);

		if (employee.isPresent()) {
			return employee.get();
		} else {
			throw new RecordNotFoundException("No appointment record exist for given id");
		}
	}

	public Appointments createOrUpdateAppointments(Appointments appointment) {
		// TODO Auto-generated method stub
		if(appointment.getId()!=null) {
		Optional<Appointments> appointmentData = repository.findById(appointment.getId());

		if (appointmentData.isPresent()) {
			Appointments newEntity = appointmentData.get();
			newEntity.setDate(appointment.getDate());
			newEntity.setDescription(appointment.getDescription());
			newEntity.setMeetingTitle(appointment.getMeetingTitle());
			newEntity.setPhysician(appointment.getPhysician());
			newEntity.setReason(appointment.getReason());
			newEntity.setTime(appointment.getTime());

			newEntity = repository.save(newEntity);

			return newEntity;
		} 
		}else {
			appointment = repository.save(appointment);

			return appointment;
		}
		return appointment;
	}

	public void deleteAppointmentsById(Integer id) throws RecordNotFoundException {
		// TODO Auto-generated method stub
		Optional<Appointments> appointment = repository.findById(id);

		if (appointment.isPresent()) {
			repository.deleteById(id);
		} else {
			throw new RecordNotFoundException("No appointment record exist for given id");
		}
	}
}
