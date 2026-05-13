package com.erp.ms_clients.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO {

    private Long id;

    @NotBlank(message = "El RUT es obligatorio")
    @Pattern(regexp = "^[0-9]{7,8}-[0-9Kk]$", message = "Formato de RUT inválido (ej: 12345678-9)")
    private String rut;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El formato del email no es válido")
    private String email;

    @Size(min = 9, max = 15, message = "El teléfono debe tener entre 9 y 15 caracteres")
    private String telefono;

    private LocalDateTime fechaCreacion;
}
