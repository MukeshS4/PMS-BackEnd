package com.citiustech.pms.patient.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.citiustech.pms.patient.entity.Medication;

public interface MedicationRepository extends JpaRepository<Medication, Integer> {

}
