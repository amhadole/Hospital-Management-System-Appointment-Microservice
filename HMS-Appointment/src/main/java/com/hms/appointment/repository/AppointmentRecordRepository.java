package com.hms.appointment.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hms.appointment.entity.AppointmentRecord;

@Repository
public interface AppointmentRecordRepository extends JpaRepository<AppointmentRecord, Long>{
	Optional<AppointmentRecord> findByAppointmentEntity_Id(Long appointmentId);
}
