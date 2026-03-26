package com.hms.appointment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hms.appointment.entity.PrescriptionEntity;

public interface PrescriptionEntityRepository extends JpaRepository<PrescriptionEntity, Long>{

	Optional<PrescriptionEntity> findByAppointment_Id(Long appointmentId);
	
	List<PrescriptionEntity>findByPatientId(Long patientId);

}
