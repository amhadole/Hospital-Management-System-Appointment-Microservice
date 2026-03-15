package com.hms.appointment.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hms.appointment.entity.AppointmentEntity;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long>{

	boolean existsByDoctorIdAndAppointmentTime(Long doctorId, LocalDateTime appointmentTime);


}
