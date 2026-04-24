package com.erp.ms_clients.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name="clientes")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @Size(min = 9, max=10, message = "El rut debe tener entre 9 y 10 caracteres.")
    @NotNull
    private String rut;

    @NotNull
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @Email(message = "El formato del email no es válido")
    private String email;

    private String telefono;

    @Column(updatable = false)
    private LocalDateTime fechaCreacion;

    @PrePersist
    protected void onCreate(){
        fechaCreacion = LocalDateTime.now();
    }
    
}
