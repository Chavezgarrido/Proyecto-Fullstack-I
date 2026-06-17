package com.erp.ms_orders;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@OpenAPIDefinition(info = @Info(
	title = "Microservicio de órdenes",
	version = "1.0",
	description = "Documentación de los endpoints para la gestión de pedidos y órdenes de venta")
)
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class MsOrdersApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsOrdersApplication.class, args);
	}

}
