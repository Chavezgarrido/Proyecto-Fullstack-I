package com.erp.ms_purchases;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients

public class MsPurchasesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsPurchasesApplication.class, args);
	}

}
