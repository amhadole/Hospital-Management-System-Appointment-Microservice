package com.hms.appointment.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hms.appointment.dto.AppointmentDetailDto;
import com.hms.appointment.entity.AppointmentEntity;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {

	boolean existsByDoctorIdAndAppointmentTime(Long doctorId, LocalDateTime appointmentTime);

	@Query("Select new com.hms.appointment.dto.AppointmentDetailDto(a.id, a.patientId, null, null, null, a.doctorId, null, a.appointmentTime, a.status, a.reason, a.note) From AppointmentEntity a Where a.patientId =?1")
	List<AppointmentDetailDto> findAllByPatientId(Long patientId);

	@Query("Select new com.hms.appointment.dto.AppointmentDetailDto(a.id, a.patientId, null, null, null, a.doctorId, null, a.appointmentTime, a.status, a.reason, a.note) From AppointmentEntity a Where a.doctorId =?1")
	List<AppointmentDetailDto> findAllByDoctorId(Long doctorId);

}
