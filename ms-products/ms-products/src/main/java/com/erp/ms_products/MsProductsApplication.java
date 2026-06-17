package com.erp.ms_products;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info; // 🛠️ Import indispensable para que compile @Info
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@OpenAPIDefinition(
    info = @Info(
        title = "API de Gestión de Productos (ms-products)",
        version = "1.0.0",
        description = "Documentación oficial de los endpoints para el control de inventario, catálogo comercial y stock."
    )
)
@SpringBootApplication
@EnableDiscoveryClient
public class MsProductsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsProductsApplication.class, args);
    }
}