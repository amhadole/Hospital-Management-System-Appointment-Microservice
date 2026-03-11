package com.hms.appointment.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.hms.appointment.dto.ApiResponse;
import com.hms.appointment.dto.DoctorDto;
import com.hms.appointment.dto.PatientDto;

import reactor.core.publisher.Mono;

@Service
public class ApiService {
	private final WebClient.Builder webClient;
	
	public ApiService(WebClient.Builder webClient) {
		this.webClient = webClient;
	}
	
	public Mono<Boolean> doctorExists(Long id){
		return webClient.build().get().uri("http://localhost:8090/profile/doctor/exists/"+ id).retrieve().bodyToMono(new ParameterizedTypeReference<ApiResponse<Boolean>>() {
		}).map(ApiResponse::getData);
	}
	
	public Mono<Boolean> patientExists(Long id){
		return webClient.build().get().uri("http://localhost:8090/profile/patient/exists/"+ id).retrieve().bodyToMono(new ParameterizedTypeReference<ApiResponse<Boolean>>() {
		}).map(ApiResponse::getData);
	}
	
	public Mono<PatientDto> getPatientById(Long id){
		return webClient.build().get().uri("http://localhost:8090/profile/patient/get/"+ id).retrieve().bodyToMono(new ParameterizedTypeReference<ApiResponse<PatientDto>>() {
		}).map(ApiResponse::getData);
	}
	
	public Mono<DoctorDto> getDoctorById(Long id){
		return webClient.build().get().uri("http://localhost:8090/profile/doctor/get/"+ id).retrieve().bodyToMono(new ParameterizedTypeReference<ApiResponse<DoctorDto>>() {
		}).map(ApiResponse::getData);
	}
	
}
