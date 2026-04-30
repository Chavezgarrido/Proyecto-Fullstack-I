package  com.erp.ms_sales.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

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
    private String clienteRut;
    private Integer cantidad;
    private Double precioTotal;
    private LocalDateTime fechaVenta;

    @PrePersist
    public void prePersist(){
        this.fechaVenta = LocalDateTime.now();
    }
}
