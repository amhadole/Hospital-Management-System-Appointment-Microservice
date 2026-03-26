package com.hms.appointment.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hms.appointment.dto.MedicineDto;
import com.hms.appointment.entity.Medicine;
import com.hms.appointment.exception.HmsException;
import com.hms.appointment.repository.MedicineRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MedicineServiceImp implements MedicineService {
	private final MedicineRepository medicineRepository;

	public MedicineServiceImp(MedicineRepository medicineRepository) {
		this.medicineRepository = medicineRepository;
	}

	@Override
	public Long saveMedicine(MedicineDto medicineDto) throws HmsException {
		return medicineRepository.save(medicineDto.toEntity()).getId();
	}

	@Override
	public List<MedicineDto> saveAllMedicine(List<MedicineDto> medicineDto) throws HmsException {
		return ((List<Medicine>) medicineRepository.saveAll(medicineDto.stream().map(MedicineDto::toEntity).toList()))
				.stream().map(Medicine::toDto).toList();
	}

	@Override
	public List<MedicineDto> getAllMedicinesByPrescriptionId(Long prescriptionId) throws HmsException {

		return ((List<Medicine>) medicineRepository.findAllByPrescription_Id(prescriptionId)).stream()
				.map(Medicine::toDto).toList();
	}

}
