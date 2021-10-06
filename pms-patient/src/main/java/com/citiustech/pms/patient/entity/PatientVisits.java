package com.citiustech.pms.patient.entity; 
import com.fasterxml.jackson.annotation.JsonProperty; 
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table; 

@Entity
@Table(name = "patient_visits")
public class PatientVisits{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	@Column(name = "vital_sign_id")
    private Integer vitalSignsId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getVitalSignsId() {
		return vitalSignsId;
	}
	public void setVitalSignsId(Integer vitalSignsId) {
		this.vitalSignsId = vitalSignsId;
	}
	
	
   
}
