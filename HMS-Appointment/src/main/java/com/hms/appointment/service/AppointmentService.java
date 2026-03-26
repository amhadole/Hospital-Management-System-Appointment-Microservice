package com.hms.appointment.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.hms.appointment.dto.AppointmentDetailDto;
import com.hms.appointment.dto.AppointmentDto;
import com.hms.appointment.exception.HmsException;

public interface AppointmentService {
	public Long scheduleAppointment(AppointmentDto appointmentDto) throws HmsException;

	public String cancelAppointment(Long appointmentId) throws HmsException;

	public void completeAppointment(Long appoinmtentId) throws HmsException;

	public AppointmentDto getAppointmentDetail(Long appointmentId) throws HmsException;

	public abstract AppointmentDetailDto getAppointmentDetailWithName(Long appoinmtentId) throws HmsException;

	public abstract List<LocalTime> getAvailableSlots(Long doctorId, LocalDate date) throws HmsException;

	List<AppointmentDetailDto> getAllAppointmentByPatientId(Long patientId) throws HmsException;

	List<AppointmentDetailDto> getAllAppointmentByDoctorId(Long doctorId) throws HmsException;
}
