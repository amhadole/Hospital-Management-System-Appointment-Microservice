package com.hms.appointment.dto;

import java.time.LocalDateTime;

import com.hms.appointment.entity.AppointmentEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDto {
	private Long id;
	private Long patientId;
	private Long doctorId;
	private LocalDateTime appointmentTime;
	private Status status;
	private String reasone;
	private String note;
	
	public AppointmentEntity toEntity() {
		return new AppointmentEntity(this.id, this.patientId, this.doctorId, this.appointmentTime, this.status, this.reasone, this.note);
	}
	
}
