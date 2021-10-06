package com.citiustech.pms.patient.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.citiustech.pms.patient.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Integer>  {

}
