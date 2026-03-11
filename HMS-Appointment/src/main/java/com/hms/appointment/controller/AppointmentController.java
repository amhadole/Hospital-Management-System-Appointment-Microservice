package com.hms.appointment.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hms.appointment.dto.ApiResponse;
import com.hms.appointment.dto.AppointmentDetailDto;
import com.hms.appointment.dto.AppointmentDto;
import com.hms.appointment.exception.HmsException;
import com.hms.appointment.service.AppointmentService;

@RestController
@CrossOrigin
@Validated
@RequestMapping("/appointment")
public class AppointmentController {
	private final AppointmentService appointmentService;

	public AppointmentController(AppointmentService appointmentService) {
		this.appointmentService = appointmentService;
	}

	@PostMapping("/schedule")
	public ResponseEntity<ApiResponse<Long>> scheduleAppointment(@RequestBody AppointmentDto appointmentDto) {
		Long appointmentId = appointmentService.scheduleAppointment(appointmentDto);
		ApiResponse<Long> response = new ApiResponse<Long>(HttpStatus.CREATED.value(), "Appointment Schedule", appointmentId, LocalDateTime.now());
		return new ResponseEntity<ApiResponse<Long>>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/cancel/{appointmentId}")
	public ResponseEntity<ApiResponse<String>> cancelAppointment(@PathVariable Long appointmentId)throws HmsException{
		String cancelAppointment = appointmentService.cancelAppointment(appointmentId);
		ApiResponse<String> response = new ApiResponse<String>(HttpStatus.OK.value(),"Appointment Cancelled" , cancelAppointment, LocalDateTime.now());
		return new ResponseEntity<ApiResponse<String>>(response, HttpStatus.OK);
	}
	
	@GetMapping("/getDetail/{appointmentId}")
	public ResponseEntity<ApiResponse<AppointmentDto>> getAppointmentDetails(@PathVariable Long appointmentId){
		AppointmentDto appointmentDetail = appointmentService.getAppointmentDetail(appointmentId);
		ApiResponse<AppointmentDto> response = new ApiResponse<AppointmentDto>(HttpStatus.OK.value(), "Appointment Details", appointmentDetail, LocalDateTime.now());
		return new ResponseEntity<ApiResponse<AppointmentDto>>(response, HttpStatus.OK);
	}
	
	@GetMapping("/get/details/{appointmentId}")
	public ResponseEntity<ApiResponse<AppointmentDetailDto>> getAppointmentDetailWithName(@PathVariable Long appointmentId)throws HmsException{
		AppointmentDetailDto appointmentDetailWithName = appointmentService.getAppointmentDetailWithName(appointmentId);
		ApiResponse<AppointmentDetailDto> response = new ApiResponse<AppointmentDetailDto>(HttpStatus.OK.value(), "Fetched Appointment Details With Name", appointmentDetailWithName, LocalDateTime.now());
		return new ResponseEntity<ApiResponse<AppointmentDetailDto>>(response, HttpStatus.OK);
	}
}
