package com.hms.appointment.entity;

import java.time.LocalDate;

import com.hms.appointment.dto.PrescriptionDto;

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
public class PrescriptionEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long patientId;
	private Long doctorId;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "appointment_id")
	private AppointmentEntity appointment;
	private LocalDate prescriptionDate;
	private String prescriptionNote;

	public PrescriptionEntity(Long id) {
		this.id = id;
	}

	public PrescriptionDto toDto() {
		return new PrescriptionDto(id, patientId, doctorId, appointment.getId(), prescriptionDate, prescriptionNote,
				null);
	}
}
