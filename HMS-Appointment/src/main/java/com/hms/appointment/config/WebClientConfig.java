package com.hms.appointment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
	
	@Bean
	WebClient.Builder getWebClientBuilder(){
		return WebClient.builder().defaultHeader(("X-Secret-Key"), "SECRET");
	}
}
