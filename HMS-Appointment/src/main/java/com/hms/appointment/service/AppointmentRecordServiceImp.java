package com.hms.appointment.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hms.appointment.dto.AppointmentRecordDto;
import com.hms.appointment.dto.DoctorName;
import com.hms.appointment.dto.RecordDetailDto;
import com.hms.appointment.entity.AppointmentRecord;
import com.hms.appointment.exception.HmsException;
import com.hms.appointment.feign.client.ProfileClient;
import com.hms.appointment.repository.AppointmentRecordRepository;
import com.hms.appointment.utility.StringListConverter;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AppointmentRecordServiceImp implements AppointmentRecordService {
	private final AppointmentRecordRepository appointmentRecordRepository;
	private final PrescriptionService prescriptionService;
	private final ProfileClient profileClient;

	public AppointmentRecordServiceImp(AppointmentRecordRepository appointmentRecordRepository,
			PrescriptionService prescriptionService, ProfileClient profileClient) {
		this.appointmentRecordRepository = appointmentRecordRepository;
		this.prescriptionService = prescriptionService;
		this.profileClient = profileClient;
	}

	@Override
	public Long createAppointmentRecord(AppointmentRecordDto appointmentRecordDto) throws HmsException {
		Optional<AppointmentRecord> existingRecord = appointmentRecordRepository
				.findByAppointmentEntity_Id(appointmentRecordDto.getAppointmentId());
		if (existingRecord.isPresent()) {
			throw new HmsException("APPOINTMENT_RECORD_ALREADY_EXISTS");
		}
		Long id = appointmentRecordRepository.save(appointmentRecordDto.toEntity()).getId();
		System.out.println(id);
		if (appointmentRecordDto.getPrescription() != null) {
			appointmentRecordDto.getPrescription().setAppointmentId(appointmentRecordDto.getAppointmentId());
			prescriptionService.savePrescription(appointmentRecordDto.getPrescription());
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
		records.setPrescription(prescriptionService.getPrescriptionByAppointmentId(appointmentId));
		return records;
	}

	@Override
	public List<RecordDetailDto> getAppointmentRecordByPatientId(Long patientId) throws HmsException {

		List<AppointmentRecord> records = appointmentRecordRepository.findByPatientId(patientId);
		List<RecordDetailDto> recordDetailDto = records.stream().map(AppointmentRecord::toRecordDetailDto).toList();
		List<Long> doctorIds = recordDetailDto.stream().map(RecordDetailDto::getDoctorId).distinct().toList();
		List<DoctorName> doctor= profileClient.getDoctorsById(doctorIds).getData();
		Map<Long, String> doctorMap = doctor.stream().collect(Collectors.toMap(DoctorName:: getId, DoctorName::getName));
		recordDetailDto.forEach(record->{
			String doctorName = doctorMap.get(record.getDoctorId());
			if(doctorName != null) {
				record.setDoctorName(doctorName);
			}else {
				record.setDoctorName("Unknown Doctor");
			}
		});
		return recordDetailDto;
		
	}

	@Override
	public Boolean isRecordExists(Long appointmentId) throws HmsException {
		
		return appointmentRecordRepository.existsByAppointmentEntity_Id(appointmentId);
	}

}
