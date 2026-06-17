package com.erp.ms_clients.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ClientDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void dto_CuandoDatosSonValidos_NoDeberiaTenerErroresDeValidacion() {
        ClientDTO dto = new ClientDTO(
            1L, 
            "12345678-9", 
            "Juan Perez", 
            "juan@erp.com", 
            "+569123456", 
            null
        );

        Set<ConstraintViolation<ClientDTO>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty(), "No debería haber errores de validación");
    }

    @Test
    void dto_CuandoRutEsInvalido_DeberiaCapturarElError() {
        ClientDTO dto = new ClientDTO(
            1L, 
            "123456789", 
            "Juan Perez", 
            "juan@erp.com", 
            "+569123456", 
            null
        );

        Set<ConstraintViolation<ClientDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        
        boolean tieneMensajeCorrecto = violations.stream()
            .anyMatch(v -> v.getMessage().equals("Formato de RUT inválido (ej: 12345678-9)"));
            
        assertTrue(tieneMensajeCorrecto);
    }

    @Test
    void dto_CuandoEmailEsInvalido_DeberiaCapturarElError() {
        ClientDTO dto = new ClientDTO(
            1L, 
            "12345678-9", 
            "Juan Perez", 
            "juan-invalido.com",
            "+569123456", 
            null
        );

        Set<ConstraintViolation<ClientDTO>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        
        boolean tieneMensajeCorrecto = violations.stream()
            .anyMatch(v -> v.getMessage().equals("El formato del email no es válido"));
            
        assertTrue(tieneMensajeCorrecto);
    }

    @Test
    void dto_CuandoCamposObligatoriosEstanVacios_DeberiaCapturarLosErrores() {
        ClientDTO dto = new ClientDTO();
        dto.setRut("");
        dto.setNombre("");
        dto.setEmail("");

        Set<ConstraintViolation<ClientDTO>> violations = validator.validate(dto);

        assertTrue(violations.size() >= 3);
    }
}