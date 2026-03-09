package com.hms.appointment.entity;

import java.time.LocalDateTime;

import com.hms.appointment.dto.AppointmentDto;
import com.hms.appointment.dto.Status;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AppointmentEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long patientId;
	private Long doctorId;
	private LocalDateTime appointmentTime;
	private Status status;
	private String reasone;
	private String note;
	
	public AppointmentDto toDto() {
		return new AppointmentDto(this.id, this.patientId, this.doctorId, this.appointmentTime, this.status, this.reasone, this.note);
	}
	
}
	
