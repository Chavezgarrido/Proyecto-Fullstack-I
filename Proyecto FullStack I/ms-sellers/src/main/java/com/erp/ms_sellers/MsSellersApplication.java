package com.erp.ms_sellers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@EnableDiscoveryClient
@SpringBootApplication
public class MsSellersApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsSellersApplication.class, args);
	}

}
