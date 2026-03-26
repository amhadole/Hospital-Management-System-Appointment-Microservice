package com.hms.appointment.service;

import java.util.List;

import com.hms.appointment.dto.MedicineDto;
import com.hms.appointment.exception.HmsException;

public interface MedicineService {
	public Long saveMedicine(MedicineDto medicineDto)throws HmsException;
	
	public List<MedicineDto> saveAllMedicine(List<MedicineDto> medicineDto)throws HmsException;
	
	public List<MedicineDto> getAllMedicinesByPrescriptionId(Long prescriptionId)throws HmsException;
}
