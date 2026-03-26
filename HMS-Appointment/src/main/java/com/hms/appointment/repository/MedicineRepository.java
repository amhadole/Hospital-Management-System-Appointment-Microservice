package com.hms.appointment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hms.appointment.entity.Medicine;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long>{
	
	List<Medicine> findAllByPrescription_Id(Long prescriptionId);
}
