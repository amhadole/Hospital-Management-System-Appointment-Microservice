package com.hms.appointment.service;

import org.springframework.stereotype.Service;

import com.hms.appointment.dto.AppointmentDetailDto;
import com.hms.appointment.dto.AppointmentDto;
import com.hms.appointment.dto.DoctorDto;
import com.hms.appointment.dto.PatientDto;
import com.hms.appointment.dto.Status;
import com.hms.appointment.entity.AppointmentEntity;
import com.hms.appointment.exception.HmsException;
import com.hms.appointment.repository.AppointmentRepository;

@Service
public class AppointmentServiceImp implements AppointmentService {
	private final AppointmentRepository appointmentRepository;
	private final ApiService apiService;

	public AppointmentServiceImp(AppointmentRepository appointmentRepository, ApiService apiService) {
		this.appointmentRepository = appointmentRepository;
		this.apiService = apiService;
	}

	@Override
	public Long scheduleAppointment(AppointmentDto appointmentDto) throws HmsException {
		Boolean doctorExists = apiService.doctorExists(appointmentDto.getDoctorId()).block();
		if (doctorExists == null || !doctorExists) {
			throw new HmsException("DOCTOR_NOT_FOUND");
		}
		Boolean patientExists = apiService.patientExists(appointmentDto.getPatientId()).block();
		if (patientExists == null || !patientExists) {
			throw new HmsException("PATIENT_NOT_FOUND");
		}
		appointmentDto.setStatus(Status.SCHEDULED);
		return appointmentRepository.save(appointmentDto.toEntity()).getId();

	}

	@Override
	public String cancelAppointment(Long appointmentId) throws HmsException {
		AppointmentEntity appointmentEntity = appointmentRepository.findById(appointmentId)
				.orElseThrow(() -> new HmsException("APPOINTMENT_NOT_FOUND"));
		if (appointmentEntity.getStatus().equals(Status.CANCELLED)) {
			throw new HmsException("APOINTMENT_ALREADY_CANCELLED");
		}
		appointmentEntity.setStatus(Status.CANCELLED);
		appointmentRepository.save(appointmentEntity);
		return "Appointment Cancelled";

	}

	@Override
	public AppointmentDto getAppointmentDetail(Long appointmentId) throws HmsException {
		return appointmentRepository.findById(appointmentId)
				.orElseThrow(() -> new HmsException("APPOINTMENT_NOT_FOUND")).toDto();

	}

	@Override
	public void completeAppointment(Long appoinmtentId) throws HmsException {

	}

	@Override
	public AppointmentDetailDto getAppointmentDetailWithName(Long appointmentId) throws HmsException {
		AppointmentDto dto = appointmentRepository.findById(appointmentId)
				.orElseThrow(() -> new HmsException("APPOINTMENT_NOT_FOUND")).toDto();
		DoctorDto doctorDto = apiService.getDoctorById(dto.getDoctorId()).block();
		PatientDto patientDto = apiService.getPatientById(dto.getPatientId()).block();
		return new AppointmentDetailDto(dto.getId(), dto.getPatientId(), patientDto.getName(), dto.getDoctorId(),
				doctorDto.getName(), dto.getAppointmentTime(), dto.getStatus(), dto.getReason(), dto.getNote());
	}

}
