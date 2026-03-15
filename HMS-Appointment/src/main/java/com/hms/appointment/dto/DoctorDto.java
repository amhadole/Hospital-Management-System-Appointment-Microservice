package com.hms.appointment.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDto {
	private Long id;
	private String name;
	private String email;
	private LocalDate dob;
	private String phone;
	private String address;
	private String licenseNo;
	private String specialization;
	private String department;
	private Integer totalExp;
	
	private LocalTime workStart;
	private LocalTime workEnd;
	private LocalTime breakStart;
	private LocalTime breakEnd;
	private Integer slotDuration;
	
	
}
