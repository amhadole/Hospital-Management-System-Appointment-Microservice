package com.hms.appointment.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.hms.appointment.dto.ApiResponse;
import com.hms.appointment.dto.DoctorDto;
import com.hms.appointment.dto.PatientDto;

@FeignClient(name = "HMS-Profile")
public interface ProfileClient {
	@GetMapping("/profile/doctor/exists/{id}")
	ApiResponse<Boolean> doctorExists(@PathVariable Long id);
	
	@GetMapping("/profile/patient/exists/{id}")
	ApiResponse<Boolean> patientExists(@PathVariable Long id);
	
	@GetMapping("/profile/patient/get/{id}")
	ApiResponse<PatientDto> getPatientById(@PathVariable Long id);
	
	@GetMapping("/profile/doctor/get/{id}")
	ApiResponse<DoctorDto> getDoctorById(@PathVariable Long id);
	
}
