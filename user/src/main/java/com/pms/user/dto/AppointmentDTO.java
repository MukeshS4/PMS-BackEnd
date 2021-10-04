package com.pms.user.dto;

import com.pms.user.entity.UserData;
import com.pms.user.entity.Patient;

public class AppointmentDTO {

	private String appointmentId;
	private UserData employee;
	private String date;
	private String time;
	private Patient patient;
	private String description;
	public UserData getEmployee() {
		return employee;
	}
	public void setEmployee(UserData employee) {
		this.employee = employee;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAppointmentId() {
		return appointmentId;
	}
	public void setAppointmentId(String appointmentId) {
		this.appointmentId = appointmentId;
	}
}
