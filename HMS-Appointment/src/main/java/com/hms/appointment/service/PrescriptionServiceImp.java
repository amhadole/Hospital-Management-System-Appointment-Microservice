package com.hms.appointment.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hms.appointment.dto.DoctorName;
import com.hms.appointment.dto.PrescriptionDetailsDto;
import com.hms.appointment.dto.PrescriptionDto;
import com.hms.appointment.entity.PrescriptionEntity;
import com.hms.appointment.exception.HmsException;
import com.hms.appointment.feign.client.ProfileClient;
import com.hms.appointment.repository.PrescriptionEntityRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PrescriptionServiceImp implements PrescriptionService {
	private final PrescriptionEntityRepository prescriptionEntityRepository;
	private final MedicineService medicineService;
	private final ProfileClient profileClient;
	 
	public PrescriptionServiceImp(PrescriptionEntityRepository prescriptionEntityRepository,
			MedicineService medicineService, ProfileClient profileClient) {
		this.prescriptionEntityRepository = prescriptionEntityRepository;
		this.medicineService = medicineService;
		this.profileClient = profileClient;
	}

	@Override
	public Long savePrescription(PrescriptionDto prescriptionDto) throws HmsException {
		Long id = prescriptionEntityRepository.save(prescriptionDto.toEntity()).getId();
		prescriptionDto.getMedicines().forEach(medicine -> {
			medicine.setPrescriptionId(id);
		});
		medicineService.saveAllMedicine(prescriptionDto.getMedicines());
		return id;
	}

	@Override
	public PrescriptionDto getPrescriptionByAppointmentId(Long appointmentId) throws HmsException {
		PrescriptionDto dto = prescriptionEntityRepository.findByAppointment_Id(appointmentId)
				.orElseThrow(() -> new HmsException("PRISCRIPTION_NOT_FOUND")).toDto();
		dto.setMedicines(medicineService.getAllMedicinesByPrescriptionId(dto.getId()));
		return dto;
	}

	@Override
	public PrescriptionDto getPrescriptionById(Long prescriptionId) throws HmsException {
		PrescriptionDto dto = prescriptionEntityRepository.findById(prescriptionId).orElseThrow(()-> new HmsException("PRISCRIPTION_NOT_FOUND")).toDto();
		dto.setMedicines(medicineService.getAllMedicinesByPrescriptionId(dto.getId()));
		return dto;
	}

	@Override
	public List<PrescriptionDetailsDto> getPrescriptionByPatientId(Long patientId) throws HmsException {
		List<PrescriptionEntity> prescriptionEntities = prescriptionEntityRepository.findByPatientId(patientId);
		List<PrescriptionDetailsDto> prescriptionDetailsDto = prescriptionEntities.stream().map(PrescriptionEntity::toPrescriptionDetailsDto).toList();
		prescriptionDetailsDto.forEach(details ->{
			details.setMedicines(medicineService.getAllMedicinesByPrescriptionId(details.getId()));
		});
		List<Long> doctorId = prescriptionDetailsDto.stream().map(PrescriptionDetailsDto::getDoctorId).distinct().toList();
		List<DoctorName> doctorNames = profileClient.getDoctorsById(doctorId).getData();
		Map<Long, String> doctorMap = doctorNames.stream().collect(Collectors.toMap(DoctorName::getId, DoctorName::getName));
		prescriptionDetailsDto.forEach(details->{
			String doctorName = doctorMap.get(details.getDoctorId());
			if(doctorName!=null) {
				details.setDoctorName(doctorName);
			}else {
				details.setDoctorName("Unknown Doctor");
			}
		});
		return prescriptionDetailsDto;
	}

}
