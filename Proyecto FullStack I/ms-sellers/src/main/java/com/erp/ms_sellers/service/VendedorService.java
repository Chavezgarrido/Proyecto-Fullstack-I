package main.java.com.erp.ms_sellers.service;


import com.erp.ms_sellers.model.Vendedor;
import com.erp.ms_sellers.repository.VendedorRepository;
import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service

@RequiredArgsConstructor
public class VendedorService {

    
    private final VendedorRepository vendedorRepository;

    
    @Transactional(readOnly = true)
    public List<Vendedor> obtenerTodos() {
        
        return vendedorRepository.findAll();
    }

    
    @Transactional(readOnly = true)
    public Vendedor obtenerPorId(Long id) {
       
        return vendedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vendedor id " + id + " no encontrado."));
    }

    
    @Transactional(readOnly = true)
    public Vendedor buscarPorRut(String rut) {
        
        return vendedorRepository.findByRut(rut)
                .orElseThrow(() -> new RuntimeException("Vendedor con rut " + rut + " no encontrado."));
    }

    
    @Transactional
    public Vendedor crear(Vendedor vendedor) {
        
        String rutLimpio = vendedor.getRut().replace(".", "").replace(" ", "").toUpperCase();
        
        vendedor.setRut(rutLimpio);
        
        
        if (vendedorRepository.findByRut(vendedor.getRut()).isPresent()) {
            
            throw new RuntimeException("El rut " + vendedor.getRut() + " ya está ingresado.");
        }
      
        return vendedorRepository.save(vendedor);
    }

    
    @Transactional
    public void eliminar(String rut) {
        
        Vendedor vendedor = buscarPorRut(rut);
        
        vendedorRepository.delete(vendedor);
    }
    @Transactional
    public Vendedor registrarVenta(Long id) {
        
        Vendedor vendedor = obtenerPorId(id);
        
        
        int nuevasVentas = vendedor.getCantidadVentas() + 1;
        vendedor.setCantidadVentas(nuevasVentas);
        
        
        
        double nuevoBono = nuevasVentas * 5000.0;
        vendedor.setBonificacion(nuevoBono);
        
        
        return vendedorRepository.save(vendedor);
    }
}