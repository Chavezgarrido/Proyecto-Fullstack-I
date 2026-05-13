package com.erp.ms_sellers.service;

import com.erp.ms_sellers.dto.SellerDTO;
import com.erp.ms_sellers.model.Seller;
import com.erp.ms_sellers.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SellerService {

    private final SellerRepository sellerRepository;

    private SellerDTO convertToDTO(Seller s){
        SellerDTO dto = new SellerDTO();
        dto.setId(s.getId());
        dto.setRut(s.getRut());
        dto.setNombre(s.getNombre());
        dto.setApellido(s.getApellido());
        dto.setEmail(s.getEmail());
        dto.setPorcentajeComision(s.getPorcentajeComision());
        dto.setSucursal(s.getSucursal());
        dto.setActivo(s.isActivo());
        return dto;
    }

    @Transactional(readOnly = true)
    public List<SellerDTO> getAll(){
        log.info("Listando todos los vendedores");
        return sellerRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SellerDTO getByRut(String rut){
        String rutLimpio = rut.replace(".", "").replace("-", "").toUpperCase().trim();
        log.info("Buscando vendedor con rut " + rutLimpio);
        return sellerRepository.findByRut(rutLimpio).map(this::convertToDTO)
            .orElseThrow(() -> new RuntimeException("Vendedor con rut " + rutLimpio + " no encontrado"));
    }

    @Transactional
    public SellerDTO create(SellerDTO dto){
        log.info("Creando nuevo vendedor");
        String rutLimpio = dto.getRut().replace(".", "").replace("-", "").toUpperCase().trim();

        if(sellerRepository.existsByRut(rutLimpio)){
            log.warn("El rut " + rutLimpio + " ya existe");
        throw new RuntimeException("El rut " + rutLimpio + " ya está registrado en sistema");
        }

        Seller s = new Seller();
        s.setRut(rutLimpio);
        s.setNombre(dto.getNombre());
        s.setApellido(dto.getApellido());
        s.setEmail(dto.getEmail());
        s.setPorcentajeComision(dto.getPorcentajeComision() != null ? dto.getPorcentajeComision() : 0.0);
        s.setSucursal(dto.getSucursal());
        s.setActivo(true);

        return convertToDTO(sellerRepository.save(s));
    }

    @Transactional
    public SellerDTO update(Long id, SellerDTO dto){
        log.info("Actualizando vendedor id " + id);
        Seller s = sellerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Vendedor con id " + id + " no encontrado"));

        if (dto.getNombre() != null) s.setNombre(dto.getNombre());
        if (dto.getApellido() != null) s.setApellido(dto.getApellido());
        if (dto.getEmail() != null) s.setEmail(dto.getEmail());
        if (dto.getPorcentajeComision() != null) s.setPorcentajeComision(dto.getPorcentajeComision());
        if (dto.getSucursal() != null) s.setSucursal(dto.getSucursal());
        if (dto.isActivo() != s.isActivo()) s.setActivo(dto.isActivo());

        return convertToDTO(sellerRepository.save(s));
    }

    @Transactional
    public void changeState(Long id, boolean estado){
        log.info("Cambiando estado de vendedor id " + id + " a " + estado);
        Seller s = sellerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Vendedor con id " + id + " no encontrado"));
        s.setActivo(estado);
        sellerRepository.save(s);
    }

    @Transactional
    public void delete(Long id){
        log.info("Eliminando vendedor id " + id);
        if(!sellerRepository.existsById(id)){
            throw new RuntimeException("No existe vendedor con id " + id);
        }
        sellerRepository.deleteById(id);
    }
}
