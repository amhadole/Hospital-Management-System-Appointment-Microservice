package com.hms.appointment.service;

import java.util.List;

import com.hms.appointment.dto.PrescriptionDetailsDto;
import com.hms.appointment.dto.PrescriptionDto;
import com.hms.appointment.exception.HmsException;

public interface PrescriptionService {
	
	public Long savePrescription(PrescriptionDto prescriptionDto)throws HmsException;
	
	public PrescriptionDto getPrescriptionByAppointmentId(Long appointmentId)throws HmsException;
	
	public PrescriptionDto getPrescriptionById(Long prescriptionId)throws HmsException;
	
	public List<PrescriptionDetailsDto> getPrescriptionByPatientId(Long patientId)throws HmsException;
}
