package com.citiustech.pms.patient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.citiustech.pms.patient.entity.Appointments;

@Repository
public interface AppointmentsRepository extends JpaRepository<Appointments, Integer>{

}
