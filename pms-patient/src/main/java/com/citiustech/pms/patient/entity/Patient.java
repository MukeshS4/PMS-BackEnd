package com.citiustech.pms.patient.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "patient")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Patient{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "demography_id", referencedColumnName = "id")
    private Demographies demographies;
	
	
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "allergy_id", referencedColumnName = "id")
    private Allergies allergies;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Demographies getDemographies() {
		return demographies;
	}
	public void setDemographies(Demographies demographies) {
		this.demographies = demographies;
	}
	public Allergies getAllergies() {
		return allergies;
	}
	public void setAllergies(Allergies allergies) {
		this.allergies = allergies;
	}
	@Override
	public String toString() {
		return "Patient [id=" + id + "]";
	}
	
	
	
}
