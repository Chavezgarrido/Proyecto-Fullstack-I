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
    private Long vendedorId;
    private LocalDateTime fechaVenta;

    @PrePersist
    public void prePersist(){
        this.fechaVenta = LocalDateTime.now();
    }

    public void setVendedorId(Object vendedorId2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setVendedorId'");
    }
}
