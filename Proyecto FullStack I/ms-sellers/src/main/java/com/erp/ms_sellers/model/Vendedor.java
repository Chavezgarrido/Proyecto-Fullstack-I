package main.java.com.erp.ms_sellers.model;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name="vendedores")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vendedor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @Size(min = 9, max=10, message = "El rut debe tener entre 9 y 10 caracteres.")
    private String rut;

    @NotNull
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @Email(message = "El formato del email no es válido")
    private String email;

    private String telefono;

   
    private Integer cantidadVentas = 0;

    
    private Double bonificacion = 0.0;
}