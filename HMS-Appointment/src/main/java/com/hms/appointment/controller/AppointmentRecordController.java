package com.hms.appointment.controller;

import java.time.LocalDateTime;
import java.util.List;

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
import com.hms.appointment.dto.PrescriptionDetailsDto;
import com.hms.appointment.dto.RecordDetailDto;
import com.hms.appointment.exception.HmsException;
import com.hms.appointment.service.AppointmentRecordService;
import com.hms.appointment.service.PrescriptionService;

@RestController
@CrossOrigin
@Validated
@RequestMapping("/appointment/report")
public class AppointmentRecordController {
	private final AppointmentRecordService appointmentRecordService;
	private final PrescriptionService prescriptionService;

	public AppointmentRecordController(AppointmentRecordService appointmentRecordService, PrescriptionService prescriptionService) {
		this.appointmentRecordService = appointmentRecordService;
		this.prescriptionService = prescriptionService;
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
	
	@GetMapping("/getRecordsByPatientId/{patientId}")
	public ResponseEntity<ApiResponse<List<RecordDetailDto>>> getRecordsByPatientId(@PathVariable Long patientId)throws HmsException{
		List<RecordDetailDto> appointmentRecordByPatientId = appointmentRecordService.getAppointmentRecordByPatientId(patientId);
		ApiResponse<List<RecordDetailDto>> response = new ApiResponse<List<RecordDetailDto>>(HttpStatus.OK.value(), "Fetch Report", appointmentRecordByPatientId, LocalDateTime.now());
		return new ResponseEntity<ApiResponse<List<RecordDetailDto>>>(response, HttpStatus.OK);
	}
	
	@GetMapping("/isRecordExists/{appointmentId}")
	public ResponseEntity<ApiResponse<Boolean>> isRecordExists(@PathVariable Long appointmentId)throws HmsException{
		Boolean recordExists = appointmentRecordService.isRecordExists(appointmentId);
		ApiResponse<Boolean> response = new ApiResponse<Boolean>(HttpStatus.OK.value(), "Checked if Record Exists", recordExists, LocalDateTime.now());
		return new ResponseEntity<ApiResponse<Boolean>>(response,HttpStatus.OK);
	}
	
	@GetMapping("/getPrescriptionsByPatientId/{patientId}")
	public ResponseEntity<ApiResponse<List<PrescriptionDetailsDto>>> getPrescriptionByPatientId(@PathVariable Long patientId)throws HmsException {
		List<PrescriptionDetailsDto> prescriptionByPatientId = prescriptionService.getPrescriptionByPatientId(patientId);
		ApiResponse<List<PrescriptionDetailsDto>> response = new ApiResponse<List<PrescriptionDetailsDto>>(HttpStatus.OK.value(), "Fetch Prescription By Patient Id", prescriptionByPatientId, LocalDateTime.now());
		return new ResponseEntity<ApiResponse<List<PrescriptionDetailsDto>>>(response, HttpStatus.OK);
	}
}
