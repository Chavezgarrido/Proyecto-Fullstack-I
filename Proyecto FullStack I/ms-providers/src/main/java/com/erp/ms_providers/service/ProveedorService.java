package com.erp.ms_providers.service;

import com.erp.ms_providers.model.Proveedor;
import com.erp.ms_providers.repository.ProveedoresRepository;

import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProveedorService {

    private final ProveedoresRepository proveedoresRepository;

    @Transactional(readOnly = true)
    public List<Proveedor> obtenerTodos(){
        return proveedoresRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Proveedor obtenerPorId(Long id){
        return proveedoresRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Proveedor id " + id + " no encontrado"));
    }

    @Transactional(readOnly = true)
    public Proveedor buscarPorRut(String rut){
        return proveedoresRepository.findByRut(rut)
            .orElseThrow(() -> new RuntimeException("Proveedor con rut " + rut + " no encontrado."));        
    }

    @Transactional
    public Proveedor crear(Proveedor proveedor){
        String rutLimpio = proveedor.getRut().replace(".", "").replace(" ", "").toUpperCase();
        proveedor.setRut(rutLimpio);

        if(proveedoresRepository.findByRut(proveedor.getRut()).isPresent()){
            throw new RuntimeException("El rut " + proveedor.getRut() + " ya esta ingresado.");
        }
        return proveedoresRepository.save(proveedor);
    }

    @Transactional
    public void eliminar(String rut){
        Proveedor proveedor = buscarPorRut(rut);
        proveedoresRepository.delete(proveedor);
    }
}
