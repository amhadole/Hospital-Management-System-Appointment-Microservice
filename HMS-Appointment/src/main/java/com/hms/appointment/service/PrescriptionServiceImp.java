package com.hms.appointment.service;

import org.springframework.stereotype.Service;

import com.hms.appointment.dto.PrescriptionDto;
import com.hms.appointment.exception.HmsException;
import com.hms.appointment.repository.PrescriptionEntityRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PrescriptionServiceImp implements PrescriptionService {
	private final PrescriptionEntityRepository prescriptionEntityRepository;
	private final MedicineService medicineService;

	public PrescriptionServiceImp(PrescriptionEntityRepository prescriptionEntityRepository,
			MedicineService medicineService) {
		this.prescriptionEntityRepository = prescriptionEntityRepository;
		this.medicineService = medicineService;
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

}
