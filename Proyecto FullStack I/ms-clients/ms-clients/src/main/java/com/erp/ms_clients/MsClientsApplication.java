package com.erp.ms_clients;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MsClientsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsClientsApplication.class, args);
	}

}
