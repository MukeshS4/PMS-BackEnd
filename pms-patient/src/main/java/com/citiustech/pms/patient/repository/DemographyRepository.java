package com.citiustech.pms.patient.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.citiustech.pms.patient.entity.Demographies;
import com.citiustech.pms.patient.entity.Patient;

public interface DemographyRepository extends JpaRepository<Demographies, Integer>  {

}
