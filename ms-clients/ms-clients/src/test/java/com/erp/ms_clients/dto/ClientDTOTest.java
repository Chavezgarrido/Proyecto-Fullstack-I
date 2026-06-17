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

    // El motor de validación estándar de Jakarta. Es el encargado de escanear el objeto y buscar anotaciones como @NotNull, @Pattern, etc.
    private Validator validator;

    @BeforeEach
    void setUp() {
        // Inicializa manualmente el motor de validación (Hibernate Validator por debajo) antes de cada test.
        // Esto emula exactamente lo que hace Spring Boot cuando recibe un JSON en un controlador con @Valid.
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void dto_CuandoDatosSonValidos_NoDeberiaTenerErroresDeValidacion() {
        // Arrange: Se crea un DTO con datos que cumplen perfectamente todas las reglas del negocio.
        ClientDTO dto = new ClientDTO(
            1L, 
            "12345678-9", 
            "Juan Perez", 
            "juan@erp.com", 
            "+569123456", 
            null
        );

        // Act: Le pedimos al validador que inspeccione el objeto DTO.
        // ConstraintViolation guarda los detalles de cada regla rota (qué campo falló, qué valor tenía y qué mensaje de error dio).
        Set<ConstraintViolation<ClientDTO>> violations = validator.validate(dto);

        // Assert: Si los datos son correctos, el Set de violaciones debe estar completamente vacío.
        assertTrue(violations.isEmpty(), "No debería haber errores de validación");
    }

    @Test
    void dto_CuandoRutEsInvalido_DeberiaCapturarElError() {
        // Arrange: Enviamos un RUT sin guión ni dígito verificador separado ("123456789") para forzar el fallo.
        ClientDTO dto = new ClientDTO(
            1L, 
            "123456789", 
            "Juan Perez", 
            "juan@erp.com", 
            "+569123456", 
            null
        );

        // Act: Validamos el objeto.
        Set<ConstraintViolation<ClientDTO>> violations = validator.validate(dto);
        
        // Assert: 1. El set NO debe estar vacío (sabemos que hay al menos un error).
        assertFalse(violations.isEmpty());
        
        // 2. Usamos Streams para recorrer las violaciones y confirmar que existe un error 
        // cuyo mensaje coincida EXACTAMENTE con el texto personalizado configurado en el DTO.
        boolean tieneMensajeCorrecto = violations.stream()
            .anyMatch(v -> v.getMessage().equals("Formato de RUT inválido (ej: 12345678-9)"));
            
        assertTrue(tieneMensajeCorrecto);
    }

    @Test
    void dto_CuandoEmailEsInvalido_DeberiaCapturarElError() {
        // Arrange: Mandamos una cadena de texto común ("juan-invalido.com") que no respeta la estructura de un correo electrónico.
        ClientDTO dto = new ClientDTO(
            1L, 
            "12345678-9", 
            "Juan Perez", 
            "juan-invalido.com", // Email inválido
            "+569123456", 
            null
        );

        // Act
        Set<ConstraintViolation<ClientDTO>> violations = validator.validate(dto);

        // Assert
        assertFalse(violations.isEmpty());
        
        // Comprobamos que el motor gatilló la anotación @Email (o @Pattern) del campo 'email' con su respectivo mensaje.
        boolean tieneMensajeCorrecto = violations.stream()
            .anyMatch(v -> v.getMessage().equals("El formato del email no es válido"));
            
        assertTrue(tieneMensajeCorrecto);
    }

    @Test
    void dto_CuandoCamposObligatoriosEstanVacios_DeberiaCapturarLosErrores() {
        // Arrange: Usamos el constructor vacío y seteamos cadenas de texto vacías ("") en campos mandatorios.
        ClientDTO dto = new ClientDTO();
        dto.setRut("");
        dto.setNombre("");
        dto.setEmail("");

        // Act
        Set<ConstraintViolation<ClientDTO>> violations = validator.validate(dto);

        // Assert: Esperamos que se rompan las reglas de un mínimo de 3 campos (RUT, Nombre y Email).
        // Al usar cadenas vacías "", típicamente se disparan anotaciones como @NotBlank o @Size(min = ...).
        assertTrue(violations.size() >= 3);
    }
}