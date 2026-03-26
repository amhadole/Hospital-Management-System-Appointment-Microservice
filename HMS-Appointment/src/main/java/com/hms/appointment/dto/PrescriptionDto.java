package com.hms.appointment.dto;

import java.time.LocalDate;
import java.util.List;

import com.hms.appointment.entity.AppointmentEntity;
import com.hms.appointment.entity.PrescriptionEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionDto {
	private Long id;
	private Long patientId;
	private Long doctorId;
	private Long appointmentId;
	private LocalDate prescriptionDate;
	private String prescriptionNote;
	private List<MedicineDto> medicines;
	
	public PrescriptionEntity toEntity() {
		return new PrescriptionEntity(id, patientId, doctorId, new AppointmentEntity(appointmentId), prescriptionDate, prescriptionNote);
	}
}
