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
import com.hms.appointment.dto.AppointmentRecordDto;
import com.hms.appointment.exception.HmsException;
import com.hms.appointment.service.AppointmentRecordService;

@RestController
@CrossOrigin
@Validated
@RequestMapping("/appointment/report")
public class AppointmentRecordController {
	private final AppointmentRecordService appointmentRecordService;

	public AppointmentRecordController(AppointmentRecordService appointmentRecordService) {
		this.appointmentRecordService = appointmentRecordService;
	}

	@PostMapping(path = "/createrecord")
	public ResponseEntity<ApiResponse<Long>> createAppointmentRecord(
			@RequestBody AppointmentRecordDto appointmentRecordDto) throws HmsException {
		Long appointmentRecord = appointmentRecordService.createAppointmentRecord(appointmentRecordDto);
		ApiResponse<Long> response = new ApiResponse<Long>(HttpStatus.CREATED.value(), "Appointment Record Created",
				appointmentRecord, LocalDateTime.now());
		return new ResponseEntity<ApiResponse<Long>>(response, HttpStatus.CREATED);
	}

	@PutMapping(path = "/updaterecord")
	public ResponseEntity<ApiResponse<String>> updateAppointmentRecord(
			@RequestBody AppointmentRecordDto appointmentRecordDto) throws HmsException {
		String updateAppointmentRecord = appointmentRecordService.updateAppointmentRecord(appointmentRecordDto);
		ApiResponse<String> response = new ApiResponse<String>(HttpStatus.OK.value(), "Appointment Record Updated",
				updateAppointmentRecord, LocalDateTime.now());
		return new ResponseEntity<ApiResponse<String>>(response, HttpStatus.OK);
	}

	@GetMapping(path = "/getByAppointmentId/{appointmentId}")
	public ResponseEntity<ApiResponse<AppointmentRecordDto>> getAppointmentRecordByAppointmentId(
			@PathVariable Long appointmentId) throws HmsException {
		AppointmentRecordDto appointmentRecordByAppointmentId = appointmentRecordService
				.getAppointmentRecordByAppointmentId(appointmentId);
		ApiResponse<AppointmentRecordDto> response = new ApiResponse<AppointmentRecordDto>(HttpStatus.OK.value(),
				"Fetch Appointment Records By Appointment Id", appointmentRecordByAppointmentId, LocalDateTime.now());
		return new ResponseEntity<ApiResponse<AppointmentRecordDto>>(response, HttpStatus.OK);
	}

	@GetMapping(path = "/getByRecordId/{recordId}")
	public ResponseEntity<ApiResponse<AppointmentRecordDto>> getAppointmentRecordById(@PathVariable Long recordId)
			throws HmsException {
		AppointmentRecordDto appointmentRecordById = appointmentRecordService.getAppointmentRecordById(recordId);
		ApiResponse<AppointmentRecordDto> response = new ApiResponse<AppointmentRecordDto>(HttpStatus.OK.value(),
				"Appointment Fetch By Appointment Record Id", appointmentRecordById, LocalDateTime.now());
		return new ResponseEntity<ApiResponse<AppointmentRecordDto>>(response, HttpStatus.OK);
	}
	
	@GetMapping(path = "/getDetailsByAppointmentId/{appointmentId}")
	public ResponseEntity<ApiResponse<AppointmentRecordDto>> getAppointmentRecordDetailsByAppointmentId(
			@PathVariable Long appointmentId) throws HmsException {
		AppointmentRecordDto appointmentRecordByAppointmentId = appointmentRecordService
				.getAppointmentRecordDetailsByAppointmentId(appointmentId);
		ApiResponse<AppointmentRecordDto> response = new ApiResponse<AppointmentRecordDto>(HttpStatus.OK.value(),
				"Fetch Appointment Records By Appointment Id", appointmentRecordByAppointmentId, LocalDateTime.now());
		return new ResponseEntity<ApiResponse<AppointmentRecordDto>>(response, HttpStatus.OK);
	}
}
