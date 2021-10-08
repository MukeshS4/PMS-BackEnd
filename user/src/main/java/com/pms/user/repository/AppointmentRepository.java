package com.pms.user.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pms.user.dto.AppointmentStats;
import com.pms.user.entity.Appointment;
import com.pms.user.entity.UserData;

@Repository
public interface AppointmentRepository extends CrudRepository<Appointment, String>{

	@Query("select time from Appointment where date=?1 and status!=?2 and employee = ?3")
	List<String> getBookedSlotByDateAndStatusAndEmployee(LocalDate localDate,int status, UserData employee);
	
	@Query(value="select * from Appointment where status in (?1)",nativeQuery = true)
	List<Appointment> getAllAppointmentByStatus(List<Integer> status);
	
	@Query(value="select new com.pms.user.dto.AppointmentStats(status,count(appointmentId) as appointmentCount) from Appointment group by status")
	List<AppointmentStats> findAppointmentStats_Named();
	
	@Query(value="select new com.pms.user.dto.AppointmentStats(status,count(appointmentId) as appointmentCount) from Appointment where employee=?1 group by status")
	List<AppointmentStats> findAppointmentStatsByEmployee_Named(UserData employee);
}
