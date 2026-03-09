package com.hms.appointment.controller;

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
	public ResponseEntity<Long> scheduleAppointment(@RequestBody AppointmentDto appointmentDto) {
		return new ResponseEntity<Long>(appointmentService.scheduleAppointment(appointmentDto), HttpStatus.CREATED);
	}
	
	@PutMapping("/cancel/{appointmentId}")
	public ResponseEntity<String> cancelAppointment(@PathVariable Long appointmentId)throws HmsException{
		appointmentService.cancelAppointment(appointmentId);
		return new ResponseEntity<String>("Appointment Cancelled", HttpStatus.OK);	
	}
	
	@GetMapping("/get/{appointmentId}")
	public ResponseEntity<AppointmentDto> getAppointmentDetails(@PathVariable Long appointmentId){
		return new ResponseEntity<AppointmentDto>(appointmentService.getAppointmentDetail(appointmentId), HttpStatus.OK);
	}
}
