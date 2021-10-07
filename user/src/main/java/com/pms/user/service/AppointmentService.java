package com.pms.user.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pms.user.constant.AppointmentStatus;
import com.pms.user.constant.TimeSlot;
import com.pms.user.dto.AppointmentDTO;
import com.pms.user.dto.AppointmentStats;
import com.pms.user.dto.EmailDTO;
import com.pms.user.dto.MessageDTO;
import com.pms.user.entity.Appointment;
import com.pms.user.entity.AppointmentEventAudit;
import com.pms.user.entity.UserData;
import com.pms.user.exception.AppointmentNotExistException;
import com.pms.user.repository.AppointmentRepository;

@Service
public class AppointmentService {
	
	@Autowired
	private AppointmentRepository appointmentRepository;
	
	@Autowired
	private AppointmentEventAuditService appointmentEventAuditService;	
	
	@Autowired
	private NotificationClient notificationClient;
	
	@Autowired
	private UserDataService userDataService;
	
	private final String CREATED_BY="N01";
	
	public boolean saveAppointmentDetails(AppointmentDTO appointmentDto) {
	boolean saveFlag = false;
	Appointment appointment=copyPropertiesFromDto(appointmentDto);
	if(appointment!=null) {
	try {
		appointment=appointmentRepository.save(appointment);
		notificationClient.sendEmail(new EmailDTO(appointment.getPatient().getEmailId(),"Appointment Booked Successfully","Hi "+appointment.getPatient().getName()+", \r\nYour"
				+ "appointment has been Successfuly created. Please find below the appointment details :\r\n"
				+ "Appointment Id : "+appointment.getAppointmentId()+"\r\n"
						+ "Physician ID :"+appointment.getEmployee().getEmployeeId()+"\r\n"
								+ "Physician Name :"+appointment.getEmployee().getFirstName()+"\r\n"
										+ "Appointment Date and Time : "+appointment.getDate()+" "+appointment.getTime()));
		notificationClient.sendSMS(new MessageDTO(appointment.getPatient().getContactNo(),"Hi "+appointment.getPatient().getName()+" ,your appointment is booked for "+appointment.getDate()+" "+appointment.getTime()));
			saveFlag = true;
			
			notificationClient.sendEmail(new EmailDTO(appointment.getEmployee().getEmailId(),"Appointment Booked Successfully", "Hi "+appointment.getEmployee().getFirstName()+" "+appointment.getEmployee().getLastName()+",\r\n You"
					+"Booked an appointment. Plese find the below the appoinment details :\r\n"
					+ "Appoinment Id :"+appointment.getAppointmentId()+"\r\n"
					+"Physician Id :"+appointment.getEmployee().getEmployeeId()+"\r\n"
					+"Appointment Date and Time :"+appointment.getDate()+" "+appointment.getTime()+"\r\n"
					+"Patient Id : "+appointment.getPatient().getPatientId()+"\r\n"
					+"Patient Name :"+appointment.getPatient().getName()));
	} catch (IllegalArgumentException e) {
		System.out.println("Exception while saving the message into database"+e.getMessage());
		e.printStackTrace();
	}
	}
	return saveFlag;
	}
	
	private Appointment copyPropertiesFromDto(AppointmentDTO appointmentDto) {
		Appointment appointment;
		try {
			appointment = new Appointment();
			DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			LocalDate localDate=LocalDate.parse(appointmentDto.getDate(),df);
			appointment.setDate(localDate);
			appointment.setDescription(appointmentDto.getDescription());
			appointment.setEmployee(appointmentDto.getEmployee());
			appointment.setPatient(appointmentDto.getPatient());
			appointment.setTime(appointmentDto.getTime());
			appointment.setStatus(AppointmentStatus.SCHEDULED.getStatus());
			appointment.setCreatedBy(CREATED_BY);
			appointment.setCreatedTs(LocalDateTime.now());

			return appointment;
		} catch (IllegalArgumentException | DateTimeParseException e) {
			System.out.println("Exception while Parsing the Date and Time "+e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public List<Appointment> getAllAppointment(){
		List<Appointment> allAppointment = new ArrayList<>();
		appointmentRepository.getAllAppointmentByStatus(Arrays.asList(AppointmentStatus.SCHEDULED.getStatus())).iterator().forEachRemaining(appointment->{
			allAppointment.add(appointment);
		});
		return allAppointment.stream().sorted(Comparator.comparing(Appointment::getDate).thenComparing(Appointment::getTime)).collect(Collectors.toList());
	}
	
	public Appointment getAppointmentByAppointmentId(String appointmentId) throws AppointmentNotExistException {
		Optional<Appointment> appointment = null;
		try {
			appointment = appointmentRepository.findById(appointmentId);
		} catch (IllegalArgumentException e) {
			throw new AppointmentNotExistException("Appointment Does Not Exist for "+appointmentId);
		}
		return appointment.orElse(null);
	}
	
	public void cancelAppointmentByAppointmentId(String appointmentId,String reason) throws AppointmentNotExistException {
		try {
			Appointment appointment=getAppointmentByAppointmentId(appointmentId);
			appointment.setStatus(AppointmentStatus.CANCELLED.getStatus());
			appointmentRepository.save(appointment);
			notificationClient.sendEmail(new EmailDTO(appointment.getPatient().getEmailId(),"Appointment Cancelled","Hi "+appointment.getPatient().getName()+", \r\nYour"
					+ "appointment has been Cancelled. Please find below the appointment details :\r\n"
					+ "Appointment Id : "+appointment.getAppointmentId()+"\r\n"
					+ "Reason : "+reason+"\r\n"
							+ "Physician ID :"+appointment.getEmployee().getEmployeeId()+"\r\n"
									+ "Physician Name :"+appointment.getEmployee().getFirstName()+"\r\n"
											+ "Appointment Date and Time : "+appointment.getDate()+" "+appointment.getTime()));
			notificationClient.sendSMS(new MessageDTO(appointment.getPatient().getContactNo(),"Hi "+appointment.getPatient().getName()+" ,your appointment is cancelled for "+appointment.getDate()+" "+appointment.getTime()));
			AppointmentEventAudit appointmentEventAudit=new AppointmentEventAudit(LocalDateTime.now(),appointment.getEmployee(),reason,appointment);
			appointmentEventAuditService.createEventAudit(appointmentEventAudit);
			
			notificationClient.sendEmail(new EmailDTO(appointment.getEmployee().getEmailId(),"Appointment Cancelled", "Hi "+appointment.getEmployee().getFirstName()+" "+appointment.getEmployee().getLastName()+",\r\n You"
					+"Just cancel an appointment. Plese find the below the appoinment details :\r\n"
					+ "Appoinment Id :"+appointment.getAppointmentId()+"\r\n"
					+"Physician Id :"+appointment.getEmployee().getEmployeeId()+"\r\n"
					+"Appointment Date and Time :"+appointment.getDate()+" "+appointment.getTime()+"\r\n"
					+"Patient Id : "+appointment.getPatient().getPatientId()+"\r\n"
					+"Patient Name :"+appointment.getPatient().getName()));
		} catch (IllegalArgumentException e) {
			System.out.println("Exception while cancelling the appointment"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public boolean updateAppointmentByAppointmentId(String appointmentId, AppointmentDTO appointmentDto) throws AppointmentNotExistException {
		boolean saveFlag = false;
		Appointment existingAppointment = getAppointmentByAppointmentId(appointmentId);
		if(existingAppointment==null) {
			throw new AppointmentNotExistException("Appointment Does Not Exist for "+appointmentId);
		}
		else
		{
			try {
				AppointmentEventAudit appointmentEventAudit=new AppointmentEventAudit(LocalDateTime.now(),appointmentDto.getEmployee(),existingAppointment.getDescription(),existingAppointment);
				DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				LocalDate localDate=LocalDate.parse(appointmentDto.getDate(),df);
				existingAppointment.setDate(localDate);
				existingAppointment.setDescription(appointmentDto.getDescription());
				existingAppointment.setEmployee(appointmentDto.getEmployee());
				existingAppointment.setTime(appointmentDto.getTime());
				existingAppointment.setModifiedBy(CREATED_BY);
				existingAppointment.setModifiedTs(LocalDateTime.now());
				appointmentRepository.save(existingAppointment);
				notificationClient.sendEmail(new EmailDTO(existingAppointment.getPatient().getEmailId(),"Appointment Re-Scheduled Successfully","Hi "+existingAppointment.getPatient().getName()+", \r\nYour"
						+ "appointment has been re-scheduled. Please find below the appointment details :\r\n"
						+ "Appointment Id : "+existingAppointment.getAppointmentId()+"\r\n"
								+ "Physician ID :"+existingAppointment.getEmployee().getEmployeeId()+"\r\n"
										+ "Physician Name :"+existingAppointment.getEmployee().getFirstName()+"\r\n"
												+ "Appointment Date and Time : "+existingAppointment.getDate()+" "+existingAppointment.getTime()));
				notificationClient.sendSMS(new MessageDTO(existingAppointment.getPatient().getContactNo(),"Hi "+existingAppointment.getPatient().getName()+" ,your appointment is rescheduled for "+existingAppointment.getDate()+" "+existingAppointment.getTime()));
				appointmentEventAuditService.createEventAudit(appointmentEventAudit);
				saveFlag = true;
				
				notificationClient.sendEmail(new EmailDTO(existingAppointment.getEmployee().getEmailId(),"Appointment Re-Scheduled Successfully", "Hi "+existingAppointment.getEmployee().getFirstName()+" "+existingAppointment.getEmployee().getLastName()+",\r\n You"
						+"re-scheduled an appointment. Plese find the below the appoinment details :\r\n"
						+ "Appoinment Id :"+existingAppointment.getAppointmentId()+"\r\n"
						+"Physician Id :"+existingAppointment.getEmployee().getEmployeeId()+"\r\n"
						+"Appointment Date and Time :"+existingAppointment.getDate()+" "+existingAppointment.getTime()+"\r\n"
						+"Patient Id : "+existingAppointment.getPatient().getPatientId()+"\r\n"
						+"Patient Name :"+existingAppointment.getPatient().getName()));
			} catch (IllegalArgumentException | DateTimeParseException e) {
				System.out.println("Exception while updating the appointment"+e.getMessage());
				e.printStackTrace();
			}
			
			
		}
		return saveFlag;
	}
	
	public List<String> getAllAvailableSlot(String date,String empId){
		List<String> timeSlot = new ArrayList<>();
		try {
			UserData userData=userDataService.getUserDataByEmployeeId(empId);
			Arrays.asList(TimeSlot.values()).stream().forEach(slot->timeSlot.add(slot.getTimeSlot()));
			DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			LocalDate localDate=LocalDate.parse(date,df);
			List<String> bookedSlot=appointmentRepository.getBookedSlotByDateAndStatusAndEmployee(localDate,AppointmentStatus.CANCELLED.getStatus(),userData);
			timeSlot.removeAll(bookedSlot);
		} catch (DateTimeParseException | IllegalArgumentException e) {
			System.out.println("Exception while checking for time slot"+e.getMessage());
			e.printStackTrace();
		}
		return timeSlot;
	}
	
	public List<Long> getAppointmentStats(String empId){
		List<Long> stats=new ArrayList<>();
		List<AppointmentStats> appointmentStats=new ArrayList<>();
		Map<Integer,Long> statsMap=new HashMap<>();
		if(empId==null)
		{
			appointmentStats=appointmentRepository.findAppointmentStats_Named();			
		}
		else
		{
			appointmentStats=appointmentRepository.findAppointmentStatsByEmployee_Named(userDataService.getUserDataByEmailId(empId));
		}
		statsMap=appointmentStats.stream().collect(Collectors.toMap(AppointmentStats::getStatus, AppointmentStats::getAppointmentCount));

		statsMap.putIfAbsent(AppointmentStatus.SCHEDULED.getStatus(), 0l);
		statsMap.putIfAbsent(AppointmentStatus.CANCELLED.getStatus(), 0l);
		statsMap.putIfAbsent(AppointmentStatus.COMPLETED.getStatus(), 0l);
		
		stats.add(statsMap.values().stream().reduce(0l, Long::sum));
		stats.add(statsMap.get(AppointmentStatus.SCHEDULED.getStatus()));
		stats.add(statsMap.get(AppointmentStatus.CANCELLED.getStatus()));
		stats.add(statsMap.get(AppointmentStatus.COMPLETED.getStatus()));
		return stats;
	}

}
