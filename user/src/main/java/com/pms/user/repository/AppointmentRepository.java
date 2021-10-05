package com.pms.user.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pms.user.entity.Appointment;

@Repository
public interface AppointmentRepository extends CrudRepository<Appointment, String>{

	@Query("select time from Appointment where date=?1 and status!=?2")
	List<String> getBookedSlotByDateAndStatus(LocalDate localDate,int status);
	
	@Query(value="select * from Appointment where status in (?1)",nativeQuery = true)
	List<Appointment> getAllAppointmentByStatus(List<Integer> status);
}
