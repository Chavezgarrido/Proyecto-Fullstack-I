package  com.erp.ms_sales.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "ventas")
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productoId;

    private Integer cantidad;

    @Min(value = 1, message = "El precio total debe ser superior a $1") 
    private Double precioTotal;
    
    private LocalDateTime fechaVenta;

    @PrePersist
    public void prePersist(){
        this.fechaVenta = LocalDateTime.now();
    }
}
