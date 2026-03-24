package com.hms.appointment.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hms.appointment.dto.AppointmentDetailDto;
import com.hms.appointment.dto.AppointmentDto;
import com.hms.appointment.dto.DoctorDto;
import com.hms.appointment.dto.PatientDto;
import com.hms.appointment.dto.Status;
import com.hms.appointment.entity.AppointmentEntity;
import com.hms.appointment.exception.HmsException;
import com.hms.appointment.feign.client.ProfileClient;
import com.hms.appointment.repository.AppointmentRepository;

@Service
public class AppointmentServiceImp implements AppointmentService {
	private final AppointmentRepository appointmentRepository;
	private final ProfileClient profileClient;

	public AppointmentServiceImp(AppointmentRepository appointmentRepository, ProfileClient profileClient) {
		this.appointmentRepository = appointmentRepository;
		this.profileClient = profileClient;
	}

	@Override
	public Long scheduleAppointment(AppointmentDto appointmentDto) throws HmsException {
		Boolean doctorExists = profileClient.doctorExists(appointmentDto.getDoctorId()).getData();
		if (doctorExists == null || !doctorExists) {
			throw new HmsException("DOCTOR_NOT_FOUND");
		}
		Boolean patientExists = profileClient.patientExists(appointmentDto.getPatientId()).getData();
		if (patientExists == null || !patientExists) {
			throw new HmsException("PATIENT_NOT_FOUND");
		}
		DoctorDto doctor = profileClient.getDoctorById(appointmentDto.getDoctorId()).getData();
		System.out.println(doctor.getBreakStart());
		System.out.println(doctor.getBreakEnd());
		LocalTime appointmentTime = appointmentDto.getAppointmentTime().toLocalTime();

		// Checking working hours of doctor
		if (appointmentTime.isBefore(doctor.getWorkStart())
		        || !appointmentTime.isBefore(doctor.getWorkEnd())) {
		    throw new HmsException("DOCTOR_NOT_AVAILABLE");
		}

		// Checking break time of doctor
		if (!appointmentTime.isBefore(doctor.getBreakStart())
		        && appointmentTime.isBefore(doctor.getBreakEnd())) {
		    throw new HmsException("DOCTOR_ON_BREAK");
		}

		boolean slotBooked = appointmentRepository.existsByDoctorIdAndAppointmentTime(appointmentDto.getDoctorId(),
				appointmentDto.getAppointmentTime());

		if (slotBooked) {
			throw new HmsException("SLOT_ALREADY_BOOKED");
		}

		appointmentDto.setStatus(Status.SCHEDULED);
		return appointmentRepository.save(appointmentDto.toEntity()).getId();

	}

	@Override
	public String cancelAppointment(Long appointmentId) throws HmsException {
		AppointmentEntity appointmentEntity = appointmentRepository.findById(appointmentId)
				.orElseThrow(() -> new HmsException("APPOINTMENT_NOT_FOUND"));
		if (appointmentEntity.getStatus().equals(Status.CANCELLED)) {
			throw new HmsException("APOINTMENT_ALREADY_CANCELLED");
		}
		appointmentEntity.setStatus(Status.CANCELLED);
		appointmentRepository.save(appointmentEntity);
		return "Appointment Cancelled";

	}

	@Override
	public AppointmentDto getAppointmentDetail(Long appointmentId) throws HmsException {
		return appointmentRepository.findById(appointmentId)
				.orElseThrow(() -> new HmsException("APPOINTMENT_NOT_FOUND")).toDto();

	}

	@Override
	public void completeAppointment(Long appoinmtentId) throws HmsException {

	}

	@Override
	public AppointmentDetailDto getAppointmentDetailWithName(Long appointmentId) throws HmsException {
		AppointmentDto dto = appointmentRepository.findById(appointmentId)
				.orElseThrow(() -> new HmsException("APPOINTMENT_NOT_FOUND")).toDto();
		DoctorDto doctorDto = profileClient.getDoctorById(dto.getDoctorId()).getData();
		PatientDto patientDto = profileClient.getPatientById(dto.getPatientId()).getData();
		return new AppointmentDetailDto(dto.getId(), dto.getPatientId(), patientDto.getName(), dto.getDoctorId(),
				doctorDto.getName(), dto.getAppointmentTime(), dto.getStatus(), dto.getReason(), dto.getNote());
	}

	@Override
	public List<LocalTime> getAvailableSlots(Long doctorId, LocalDate date) throws HmsException {
		 DoctorDto doctor =  profileClient.getDoctorById(doctorId).getData();
		 LocalTime start = doctor.getWorkStart();
		 LocalTime end = doctor.getWorkEnd();
		 int duration  = doctor.getSlotDuration();
		 
		 List<LocalTime> slots = new ArrayList<>();
		 while(start.isBefore(end)) {
			 boolean isInBreak = !start.isBefore(doctor.getBreakStart())&& start.isBefore(doctor.getBreakEnd());
			 if(!isInBreak) {
				 slots.add(start);
			 }
			 start = start.plusMinutes(duration);
		 }
		return slots;
	}

	@Override
	public List<AppointmentDetailDto> getAllAppointmentByPatientId(Long patientId) throws HmsException {
		
		return appointmentRepository.findAllByPatientId(patientId).stream()
				.map(appointment ->{
					DoctorDto doctorDto = profileClient.getDoctorById(appointment.getDoctorId()).getData();
					appointment.setDoctorName(doctorDto.getName());
					return appointment;
				}).toList();
	}

}
