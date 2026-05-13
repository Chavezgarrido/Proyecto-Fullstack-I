package com.erp.ms_providers.service;

import com.erp.ms_providers.dto.ProviderDTO;
import com.erp.ms_providers.model.Provider;
import com.erp.ms_providers.repository.ProviderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProviderService {

    private final ProviderRepository providerRepository;

    private ProviderDTO convertToDTO(Provider entity) {
        ProviderDTO dto = new ProviderDTO();
        dto.setId(entity.getId());
        dto.setRut(entity.getRut());
        dto.setRazonSocial(entity.getRazonSocial());
        dto.setCategoria(entity.getCategoria());
        dto.setContactoNombre(entity.getContactoNombre());
        dto.setEmail(entity.getEmail());
        dto.setTelefono(entity.getTelefono());
        dto.setDireccion(entity.getDireccion());
        dto.setUltimaActualizacion(entity.getUltimaActualizacion());
        return dto;
    }

    @Transactional(readOnly = true)
    public List<ProviderDTO> getAll() {
        log.info("Listando todos los proveedores registrados");
        return providerRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProviderDTO getByRut(String rut) {
        String rutLimpio = rut.replace(".", "").replace("-", "").toUpperCase().trim();
        log.info("Buscando proveedor por rut " + rutLimpio);

        return providerRepository.findByRut(rutLimpio).map(this::convertToDTO)
                .orElseThrow(() -> {
                    log.error("Proveedor con rut " + rutLimpio + " no encontrado");
                    return new RuntimeException("Proveedor rut" + rutLimpio + " no encontrado");
                });
    }

    @Transactional(readOnly = true)
    public List<ProviderDTO> searchByCategory(String categoria) {
        log.info("Buscando proveedores por la categoria " + categoria);
        return providerRepository.findByCategoria(categoria).stream().map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProviderDTO create(ProviderDTO dto) {
        log.info("Intentando registrar proveedor con rut " + dto.getRut());

        String rutNormalizado = dto.getRut().replace(".", "").replace("-", "").toUpperCase().trim();

        if (providerRepository.existsByRut(rutNormalizado)) {
            log.warn("El rut " + rutNormalizado + " ya existe en el sistema");
            throw new RuntimeException("Ya existe un proveedor con el rut " + rutNormalizado);
        }

        Provider entity = new Provider();
        entity.setRut(rutNormalizado);
        entity.setRazonSocial(dto.getRazonSocial());
        entity.setCategoria(dto.getCategoria());
        entity.setContactoNombre(dto.getContactoNombre());
        entity.setEmail(dto.getEmail());
        entity.setTelefono(dto.getTelefono());
        entity.setDireccion(dto.getDireccion());

        Provider saved = providerRepository.save(entity);
        log.info("Proveedor " + saved.getRazonSocial() + " registrado con ID " + saved.getId());

        return convertToDTO(saved);
    }

    @Transactional
    public ProviderDTO update(Long id, ProviderDTO dto) {
        log.info("Actualizacion de proveedor id " + id);

        Provider provider = providerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor con id " + id + " no encontrado"));

        if (dto.getEmail() != null)
            provider.setEmail(dto.getEmail());
        if (dto.getTelefono() != null)
            provider.setTelefono(dto.getTelefono());
        if (dto.getDireccion() != null)
            provider.setDireccion(dto.getDireccion());
        if (dto.getContactoNombre() != null)
            provider.setContactoNombre(dto.getContactoNombre());
        if (dto.getCategoria() != null)
            provider.setCategoria(dto.getCategoria());

        Provider updated = providerRepository.save(provider);
        log.info("Proveedor id " + updated.getId() + " actualizado");
        return convertToDTO(updated);
    }

    @Transactional
    public void delete(Long id) {
        log.info("Eliminando proveedor con id " + id);
        if (!providerRepository.existsById(id)) {
            log.error("Proveedor con id " + id + " inexistente en registros");
            throw new RuntimeException("El proveedor id " + id + " no existe");
        }
        providerRepository.deleteById(id);
    }

}
