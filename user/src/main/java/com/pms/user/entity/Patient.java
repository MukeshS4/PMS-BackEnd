package com.pms.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Patient {
	
	@Id
	@Column(name="Patient_Id")
	private String patientId;
	
	@Column
	private String name;
	
	@Column
	private String city;
	
	@Column
	private String country;
	
	@Column
	private String emailId;
	
	@Column
	private long contactNo;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public long getContactNo() {
		return contactNo;
	}

	public void setContactNo(long contactNo) {
		this.contactNo = contactNo;
	}

	public Patient(String patientId, String name, String city, String country, String emailId, long contactNo) {
		super();
		this.patientId = patientId;
		this.name = name;
		this.city = city;
		this.country = country;
		this.emailId = emailId;
		this.contactNo = contactNo;
	}

	@Override
	public String toString() {
		return "Patient [patientId=" + patientId + ", name=" + name + ", city=" + city + ", country=" + country
				+ ", emailId=" + emailId + ", contactNo=" + contactNo + "]";
	}

	public Patient() {
		super();
		// TODO Auto-generated constructor stub
	}

}
