package com.erp.ms_providers.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "providers")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Provider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El RUT es obligatorio")
    @Column(unique = true, nullable = false, length = 15)
    private String rut;

    @NotBlank(message = "La razón social es obligatoria")
    @Column(name = "razon_social", nullable = false)
    private String razonSocial;

    @NotBlank(message = "La categoría es obligatoria")
    private String categoria;

    private String contactoNombre;

    @Email(message = "El formato del email no es válido")
    @NotBlank(message = "El email es obligatorio")
    private String email;

    private String telefono;
    
    private String direccion;

    @Column(name = "fecha_registro", updatable = false)
    private LocalDateTime fechaRegistro;

    @Column(name = "ultima_actualizacion")
    private LocalDateTime ultimaActualizacion;

    @PrePersist
    protected void onCreate() {
        this.fechaRegistro = LocalDateTime.now();
        this.ultimaActualizacion = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.ultimaActualizacion = LocalDateTime.now();
    }
}
