package com.pms.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import com.pms.gateway.filter.GatewayRequestFilter;

@EnableEurekaClient
@SpringBootApplication
public class PmsGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(PmsGatewayApplication.class, args);
	}
	
	@Bean
	public GlobalFilter customFilter() {
	    return new GatewayRequestFilter();
	}
}
