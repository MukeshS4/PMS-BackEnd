package com.citiustech.pms.patient.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.citiustech.pms.patient.entity.Patient;
import com.citiustech.pms.patient.entity.PatientData;

public interface PatientDataRepository extends  JpaRepository<PatientData, Integer>{

PatientData findByEmailId(String emailId);
	
	PatientData deleteByEmailId(String emailId);
	@Query(value="Select * from Patient.Data pd where ", nativeQuery=true)
	List<PatientData> findAllBlocked();
	
	@Query(value="Select pd.id from Patient.Data pd ", nativeQuery=true)
	List<String> extractId();
	
}
