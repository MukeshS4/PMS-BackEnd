package com.citiustech.pms.patient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.citiustech.pms.patient.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Integer>  {

	@Query("SELECT a.email from Demographies a")
	public Patient findEmail(String email);
}
