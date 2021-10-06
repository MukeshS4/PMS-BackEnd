package com.citiustech.pms.patient.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "medication")
public class Medication{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	@Column(name = "drugID")
    private String drugID;
	@Column(name = "drugName")
    private String drugName;
	@Column(name = "drugGenName")
    private String drugGenName;
	
	@Column(name = "drugBrandName")
	private String drugBrandName;
	
	@Column(name = "drugForm")
	private String drugForm;
	
	@Column(name = "drugStrength")
	private String drugStrength;
	
	@Column(name = "patient_visit_id")
	private Integer patientVisitId ;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDrugID() {
		return drugID;
	}

	public void setDrugID(String drugID) {
		this.drugID = drugID;
	}

	public String getDrugName() {
		return drugName;
	}

	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}

	public String getDrugGenName() {
		return drugGenName;
	}

	public void setDrugGenName(String drugGenName) {
		this.drugGenName = drugGenName;
	}

	public String getDrugBrandName() {
		return drugBrandName;
	}

	public void setDrugBrandName(String drugBrandName) {
		this.drugBrandName = drugBrandName;
	}

	public String getDrugForm() {
		return drugForm;
	}

	public void setDrugForm(String drugForm) {
		this.drugForm = drugForm;
	}

	public String getDrugStrength() {
		return drugStrength;
	}

	public void setDrugStrength(String drugStrength) {
		this.drugStrength = drugStrength;
	}

	public Integer getPatientVisitId() {
		return patientVisitId;
	}

	public void setPatientVisitId(Integer patientVisitId) {
		this.patientVisitId = patientVisitId;
	}

	@Override
	public String toString() {
		return "Medication [id=" + id + ", drugID=" + drugID + ", drugName=" + drugName + ", drugGenName=" + drugGenName
				+ ", drugBrandName=" + drugBrandName + ", drugForm=" + drugForm + ", drugStrength=" + drugStrength
				+ ", patientVisitId=" + patientVisitId + "]";
	}

	
}
