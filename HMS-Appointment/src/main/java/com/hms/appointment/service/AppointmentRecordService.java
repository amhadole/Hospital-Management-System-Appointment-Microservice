package com.hms.appointment.service;

import com.hms.appointment.dto.AppointmentRecordDto;
import com.hms.appointment.exception.HmsException;

public interface AppointmentRecordService {
	public Long createAppointmentRecord(AppointmentRecordDto appointmentRecordDto) throws HmsException;

	public String updateAppointmentRecord(AppointmentRecordDto appointmentRecordDto) throws HmsException;

	public AppointmentRecordDto getAppointmentRecordByAppointmentId(Long appointmentId) throws HmsException;

	public AppointmentRecordDto getAppointmentRecordById(Long recordId) throws HmsException;
	
	public AppointmentRecordDto getAppointmentRecordDetailsByAppointmentId(Long appointmentId)throws HmsException;
}
