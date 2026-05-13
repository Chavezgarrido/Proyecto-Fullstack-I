package com.erp.ms_providers.dto;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProviderDTO {

    private Long id;

    @NotBlank(message = "El RUT es obligatorio")
    @Pattern(regexp = "^[0-9]+-[0-9kK]{1}$", message = "El formato del RUT debe ser sin puntos y con guion (ej: 12345678-9)")
    private String rut;

    @NotBlank(message = "La razón social es obligatoria")
    @Size(min = 3, max = 100, message = "La razón social debe tener entre 3 y 100 caracteres")
    private String razonSocial;

    @NotBlank(message = "La categoría es obligatoria")
    private String categoria;

    private String contactoNombre;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Debe proporcionar un email válido")
    private String email;

    private String telefono;
    
    private String direccion;

    private LocalDateTime ultimaActualizacion;
}
