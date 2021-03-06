package com.citiustech.pms.patient.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.citiustech.pms.patient.entity.Allergies;
import com.citiustech.pms.patient.entity.Demographies;
import com.citiustech.pms.patient.entity.Diagnosis;
import com.citiustech.pms.patient.entity.EmergencyContactDetails;
import com.citiustech.pms.patient.entity.Medication;
import com.citiustech.pms.patient.entity.Patient;
import com.citiustech.pms.patient.entity.PatientVisits;
import com.citiustech.pms.patient.entity.Procedure;
import com.citiustech.pms.patient.entity.VitalSigns;
import com.citiustech.pms.patient.exception.DuplicateEmailFoundException;
import com.citiustech.pms.patient.exception.FieldsNotEmptyException;
import com.citiustech.pms.patient.model.PatientForm;
import com.citiustech.pms.patient.model.PatientVisit;
import com.citiustech.pms.patient.repository.DiagnosisRepository;
import com.citiustech.pms.patient.repository.MedicationRepository;
import com.citiustech.pms.patient.repository.PatientRepository;
import com.citiustech.pms.patient.repository.PatientVisitRepository;
import com.citiustech.pms.patient.repository.ProcedureRepository;
import com.citiustech.pms.patient.repository.VitalSignsReposiitory;

@Service
public class PatientService {
	
	@Autowired
	PatientRepository patientRepository;
	
	@Autowired
	VitalSignsReposiitory vsRepo;
	@Autowired
	DiagnosisRepository diagnosisRepo;
	@Autowired
	MedicationRepository mdRepo;
	@Autowired
	PatientVisitRepository ptVisitRepo;
	@Autowired
	ProcedureRepository psRepo;
	public Patient savePatient(PatientForm patient) throws Exception {	
		Patient pt = new Patient();
		if(patient!=null) {
			//Patient pt = new Patient();
			Demographies dmo  = new Demographies();
			dmo.setAge(patient.getDemographies().getAge());
			dmo.setContact_number(patient.getDemographies().getContactNumber());
			dmo.setDate_of_birth(patient.getDemographies().getDateOfBirth());
			//dmo.setEmail(patient.getDemographies().getEmail());
			if(patient.getDemographies().getEmail()!=null) {
				Patient duplicateEmailCheck=patientRepository.findEmail(patient.getDemographies().getEmail());
				if(duplicateEmailCheck.equals(patient.getDemographies().getEmail())){
					throw new DuplicateEmailFoundException("Duplicate Email ID is present in the records");
				}
				dmo.setEmail(patient.getDemographies().getEmail());
			}
			dmo.setEthinicity(patient.getDemographies().getEthinicity());
			dmo.setFirst_name(patient.getDemographies().getFirstName());
			dmo.setGender(patient.getDemographies().getGender());
			dmo.setHome_address(patient.getDemographies().getHomeAddress());
			dmo.setLanguage(patient.getDemographies().getLanguage().toString());
			dmo.setLast_name(patient.getDemographies().getLastName());
			dmo.setRace(patient.getDemographies().getRace());
			EmergencyContactDetails ecd = new EmergencyContactDetails();
			ecd.setAddress(patient.getDemographies().getEmergencyContactDetails().getAddress());
			ecd.setContact(patient.getDemographies().getEmergencyContactDetails().getContact());
			ecd.setEmail_address(patient.getDemographies().getEmergencyContactDetails().getEmailAddress());
			ecd.setE_first_name(patient.getDemographies().getEmergencyContactDetails().getFirstName());
			ecd.setE_last_name(patient.getDemographies().getEmergencyContactDetails().getLastName());
			ecd.setPatient_portal_access(patient.getDemographies().getEmergencyContactDetails().getPatientPortalAccess());
			ecd.setRelationship(patient.getDemographies().getEmergencyContactDetails().getRelationship());
			dmo.setEmergency_contact_details(ecd);
			Allergies alr = new Allergies();
			alr.setIs_fatal(patient.getAllergies().getIsFatal());
			alr.setType(patient.getAllergies().getType());
			pt.setDemographies(dmo);
			patientRepository.save(pt);
			//return pt;
			
		}else {
			throw new FieldsNotEmptyException("Fields should not be empty");
			
		}
		return pt;
		
	}

	public Patient getPatient(Integer id) {
		// TODO Auto-generated method stub
		System.out.println("jvhjfvhf");
		return patientRepository.getById(id);
	}

	public PatientVisit savePatientVisit(PatientVisit patientvisit) {
		// TODO Auto-generated method stub
		PatientVisits pv = new PatientVisits();
		List <Diagnosis> dsList = new ArrayList();
		List <Medication> mdList = new ArrayList();
		List <Procedure> prList = new ArrayList();
		VitalSigns vs = new VitalSigns();
		vs.setBloodPressure(patientvisit.getVitalSigns().getBloodPressure());
		vs.setBodyTemp(patientvisit.getVitalSigns().getBodyTemp());
		vs.setHeight(patientvisit.getVitalSigns().getHeight());
		vs.setRespirationRate(patientvisit.getVitalSigns().getRespirationRate());
		vs.setWeight(patientvisit.getVitalSigns().getWeight());
		vsRepo.save(vs);
		pv.setVitalSignsId(vs.getId());
		ptVisitRepo.save(pv);
//		for(int i=0;i<patientvisit.getDiagnosis().size();i++) {
			Diagnosis ds = new Diagnosis();
			ds.setDescription(patientvisit.getDiagnosis().getDescription());
			ds.setDiagnosis(patientvisit.getDiagnosis().getDiagnosis());
			ds.setPatientVisitId(pv.getId());
			dsList.add(ds);
	//	}
		//for(int i=0;i<patientvisit.getMedication().size();i++) {
			Medication md = new Medication();
			md.setDrugGenName(patientvisit.getMedication().getDrugGenName());
			md.setDrugName(patientvisit.getMedication().getDrugName());
			md.setDrugID(patientvisit.getMedication().getDrugID());
			md.setDrugBrandName(patientvisit.getMedication().getDrugBrandName());
			md.setDrugForm(patientvisit.getMedication().getDrugForm());
			md.setDrugStrength(patientvisit.getMedication().getDrugStrength());
			md.setPatientVisitId(pv.getId());
			mdList.add(md);
		//}
		//for(int i=0;i<patientvisit.getProcedures().size();i++) {
			Procedure pr = new Procedure();
			pr.setDescription(patientvisit.getProcedures().getDescription());
			pr.setPatientVisitId(pv.getId());
			pr.setProcedures(patientvisit.getProcedures().getProcedures());
			prList.add(pr);
		//}
		diagnosisRepo.saveAll(dsList);
		mdRepo.saveAll(mdList);
		psRepo.saveAll(prList);
		return patientvisit;
	}

	public Object getPatientVisits() {
		// TODO Auto-generated method stub
		return ptVisitRepo.findAllPatientVisits();
	}
	
	public List<Patient> getAllPatient(){
		List<Patient> patientList=new ArrayList<>();
		patientRepository.findAll().iterator().forEachRemaining(patient->patientList.add(patient));
		return patientList;
	}
	
	public List<Demographies> getAllBlockedPatients(){
		List<Demographies> blockPatientList=new ArrayList<>();
		return blockPatientList;
	}



}
