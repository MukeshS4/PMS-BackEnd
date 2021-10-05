package com.citiustech.pms.patient.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.citiustech.pms.patient.entity.Diagnosis;

public interface DiagnosisRepository extends JpaRepository<Diagnosis, Integer> {

}
