package com.hms.appointment.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.hms.appointment.entity.AppointmentEntity;
import com.hms.appointment.entity.AppointmentRecord;
import com.hms.appointment.utility.StringListConverter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentRecordDto {
	private Long id;
	private Long patientId;
	private Long doctorId;
	private Long appointmetnId;
	private List<String> symptoms;
	private String diagnosis;
	private List<String> tests;
	private String notes;
	private String referral;
	private PrescriptionDto prescriptionDto;
	private LocalDate followUpDate;
	private LocalDateTime createdAt;

	public AppointmentRecord toEntity() {
		return new AppointmentRecord(id, patientId, doctorId, new AppointmentEntity(appointmetnId),
				StringListConverter.convertListToString(symptoms), diagnosis,
				StringListConverter.convertListToString(tests), notes, referral, followUpDate, createdAt);
	}
}
