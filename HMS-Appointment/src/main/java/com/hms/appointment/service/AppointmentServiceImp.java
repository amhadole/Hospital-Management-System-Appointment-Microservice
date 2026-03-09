package com.hms.appointment.service;

import org.springframework.stereotype.Service;

import com.hms.appointment.dto.AppointmentDto;
import com.hms.appointment.dto.Status;
import com.hms.appointment.entity.AppointmentEntity;
import com.hms.appointment.exception.HmsException;
import com.hms.appointment.repository.AppointmentRepository;

@Service
public class AppointmentServiceImp implements AppointmentService {
	private final AppointmentRepository appointmentRepository;

	public AppointmentServiceImp(AppointmentRepository appointmentRepository) {
		this.appointmentRepository = appointmentRepository;
	}

	@Override
	public Long scheduleAppointment(AppointmentDto appointmentDto) {
		return appointmentRepository.save(appointmentDto.toEntity()).getId();

	}

	@Override
	public void cancelAppointment(Long appointmentId) throws HmsException {
		AppointmentEntity appointmentEntity = appointmentRepository.findById(appointmentId)
				.orElseThrow(() -> new HmsException("APPOINTMENT_NOT_FOUND"));
		if (appointmentEntity.getStatus().equals(Status.CANCELLED)) {
			throw new HmsException("APOINTMENT_ALREADY_CANCELLED");
		}
		appointmentEntity.setStatus(Status.CANCELLED);
		appointmentRepository.save(appointmentEntity);

	}

	@Override
	public AppointmentDto getAppointmentDetail(Long appointmentId) throws HmsException {
		return appointmentRepository.findById(appointmentId)
				.orElseThrow(() -> new HmsException("APPOINTMENT_NOT_FOUND")).toDto();

	}

	@Override
	public void completeAppointment(Long appoinmtentId) throws HmsException {
		
		
	}

}
