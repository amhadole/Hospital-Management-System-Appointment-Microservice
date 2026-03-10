package com.hms.appointment.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

//import reactor.core.publisher.Mono;

@Service
public class ApiService {
	private final WebClient.Builder webClient;
	
	public ApiService(WebClient.Builder webClient) {
		this.webClient = webClient;
	}
	
//	public Mono<Boolean> isDoctorExists(){
//		
//	}

}
