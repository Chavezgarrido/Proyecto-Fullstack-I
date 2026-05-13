package com.erp.ms_clients.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name="clients")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor

public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 12)
    @Size(min = 9, max = 12, message = "El rut debe tener entre 9 y 12 caracteres.")
    @NotBlank(message = "El Rut es obligatorio")
    @Pattern(regexp = "^[0-9]{7,8}-[0-9Kk]$", message = "Formato de RUT inválido (ej: 12345678-9)")
    private String rut;

    @Column(nullable = false)
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Column(unique = true, nullable = false)
    @Email(message = "El formato del email no es válido")
    @NotBlank(message = "El email es obligatorio")
    private String email;

    @Column(length = 15)
    @Size(min = 9, max= 15, message = "El teléfono debe tener un formato válido")
    private String telefono;

    @Column(updatable = false, name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @PrePersist
    protected void onCreate(){
        fechaCreacion = LocalDateTime.now();
        fechaActualizacion = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate(){
        this.fechaActualizacion = LocalDateTime.now();
    }
}
