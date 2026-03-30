package com.hms.appointment.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.hms.appointment.dto.AppointmentRecordDto;
import com.hms.appointment.dto.RecordDetailDto;
import com.hms.appointment.utility.StringListConverter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AppointmentRecord {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long patientId;
	private Long doctorId;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "appointment_id")
	private AppointmentEntity appointmentEntity;
	private String symptoms;
	private String diagnosis;
	private String tests;
	private String notes;
	private String referral;
	private LocalDate followUpDate;
	private LocalDateTime createdAt;

	public AppointmentRecordDto toDto() {
		return new AppointmentRecordDto(id, patientId, doctorId, appointmentEntity.getId(),
				StringListConverter.convertStringToList(symptoms), diagnosis,
				StringListConverter.convertStringToList(tests), notes, referral, null, followUpDate, createdAt);
	}

	public RecordDetailDto toRecordDetailDto() {
		return new RecordDetailDto(id, patientId, doctorId, null, appointmentEntity.getId(),
				StringListConverter.convertStringToList(symptoms), diagnosis,
				StringListConverter.convertStringToList(tests), notes, referral, followUpDate, createdAt);
	}
}
