package com.citiustech.pms.patient.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.citiustech.pms.patient.entity.Procedure;

public interface ProcedureRepository extends JpaRepository<Procedure, Integer>{

}
