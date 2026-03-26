package com.hms.appointment.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hms.appointment.dto.AppointmentRecordDto;
import com.hms.appointment.entity.AppointmentRecord;
import com.hms.appointment.exception.HmsException;
import com.hms.appointment.repository.AppointmentRecordRepository;
import com.hms.appointment.utility.StringListConverter;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AppointmentRecordServiceImp implements AppointmentRecordService {
	private final AppointmentRecordRepository appointmentRecordRepository;
	private final PrescriptionService prescriptionService;

	public AppointmentRecordServiceImp(AppointmentRecordRepository appointmentRecordRepository,
			PrescriptionService prescriptionService) {
		this.appointmentRecordRepository = appointmentRecordRepository;
		this.prescriptionService = prescriptionService;
	}

	@Override
	public Long createAppointmentRecord(AppointmentRecordDto appointmentRecordDto) throws HmsException {
		Optional<AppointmentRecord> existingRecord = appointmentRecordRepository
				.findByAppointmentEntity_Id(appointmentRecordDto.getAppointmetnId());
		if (existingRecord.isPresent()) {
			throw new HmsException("APPOINTMENT_RECORD_ALREADY_EXISTS");
		}
		Long id = appointmentRecordRepository.save(appointmentRecordDto.toEntity()).getId();
		if (appointmentRecordDto.getPrescriptionDto() != null) {
			appointmentRecordDto.getPrescriptionDto().setAppointmentId(appointmentRecordDto.getAppointmetnId());
			prescriptionService.savePrescription(appointmentRecordDto.getPrescriptionDto());
		}
		return id;
	}

	@Override
	public String updateAppointmentRecord(AppointmentRecordDto appointmentRecordDto) throws HmsException {
		AppointmentRecord records = appointmentRecordRepository.findById(appointmentRecordDto.getId())
				.orElseThrow(() -> new HmsException("APPOINTMENT_RECORD_NOT_FOUND"));
		records.setSymptoms(StringListConverter.convertListToString(appointmentRecordDto.getSymptoms()));
		records.setDiagnosis(appointmentRecordDto.getDiagnosis());
		records.setTests(StringListConverter.convertListToString(appointmentRecordDto.getTests()));
		records.setNotes(appointmentRecordDto.getNotes());
		records.setReferral(appointmentRecordDto.getReferral());
		records.setFollowUpDate(appointmentRecordDto.getFollowUpDate());
		appointmentRecordRepository.save(records);
		return "Appointment Reocrd Updated Successfully";

	}

	@Override
	public AppointmentRecordDto getAppointmentRecordByAppointmentId(Long appointmentId) throws HmsException {
		return appointmentRecordRepository.findByAppointmentEntity_Id(appointmentId)
				.orElseThrow(() -> new HmsException("APPOINTMENT_RECORD_NOT_FOUND")).toDto();
	}

	@Override
	public AppointmentRecordDto getAppointmentRecordById(Long recordId) throws HmsException {
		return appointmentRecordRepository.findById(recordId)
				.orElseThrow(() -> new HmsException("APPOINTMENT_RECORD_NOT_FOUND")).toDto();
	}

	@Override
	public AppointmentRecordDto getAppointmentRecordDetailsByAppointmentId(Long appointmentId) throws HmsException {
		AppointmentRecordDto records = appointmentRecordRepository.findByAppointmentEntity_Id(appointmentId)
				.orElseThrow(() -> new HmsException("APPOINTMENT_RECORD_NOT_FOUND")).toDto();
		records.setPrescriptionDto(prescriptionService.getPrescriptionByAppointmentId(appointmentId));
		return records;
	}

}
