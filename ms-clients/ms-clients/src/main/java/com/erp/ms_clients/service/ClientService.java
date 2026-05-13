package com.erp.ms_clients.service;

import com.erp.ms_clients.dto.ClientDTO;
import com.erp.ms_clients.model.Client;
import com.erp.ms_clients.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    private ClientDTO convertToDTO(Client entity) {
        ClientDTO dto = new ClientDTO();
        dto.setId(entity.getId());
        dto.setRut(entity.getRut());
        dto.setNombre(entity.getNombre());
        dto.setEmail(entity.getEmail());
        dto.setTelefono(entity.getTelefono());
        dto.setFechaCreacion(entity.getFechaCreacion());
        return dto;
    }

    @Transactional(readOnly = true)
    public List<ClientDTO> getAll() {
        log.info("Obteniendo listado completo de clientes");
        return clientRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ClientDTO getById(Long id){
        log.info("Buscando cliente con id " +  id);
        return clientRepository.findById(id).map(this::convertToDTO)
                .orElseThrow(() -> {
                    log.error("Cliente con id " + id + " no encontrado");
                    return new RuntimeException("Cliente no encontrado");
                });
    }

    @Transactional
    public ClientDTO create(ClientDTO dto){
        log.info("Creando nuevo cliente con rut " + dto.getRut());
        String rutLimpio = dto.getRut().replace(".", "").replace(" ", "").toUpperCase();
        if(clientRepository.findByRut(rutLimpio).isPresent()){
            log.warn("Error: El rut " + rutLimpio + " ya existe");
            throw new RuntimeException("El rut " + rutLimpio + " ya existe en el sistema.");
        }

        Client entity = new Client();
        entity.setRut(rutLimpio);
        entity.setNombre(dto.getNombre());
        entity.setEmail(dto.getEmail());
        entity.setTelefono(dto.getTelefono());

        Client savedEntity = clientRepository.save(entity);
        log.info("Cliente guardado con id " + savedEntity.getId());

        return convertToDTO(savedEntity);
    }

    @Transactional
    public ClientDTO update(Long id, ClientDTO dto){
        log.info("Actualizando cliente con id " + id);
        Client existingClient = clientRepository.findById(id)
            .orElseThrow(() -> {
                log.error("No se pudo actualizar, id " + id + " no encontrado");
                return new RuntimeException("Cliente no encontrado");
            });

        existingClient.setNombre(dto.getNombre());
        existingClient.setEmail(dto.getEmail());
        existingClient.setTelefono(dto.getTelefono());   

        Client updatedEntity = clientRepository.save(existingClient);
        log.info("Cliente id " + id + " actualizado correctamente");
        return convertToDTO(updatedEntity);
    }

    @Transactional
    public void delete(Long id){
        log.info("Eliminando cliente con id " + id);

        if(!clientRepository.existsById(id)){
            log.error("No se pudo eliminar, id " + id + " no existe");
            throw new RuntimeException("No se puede eliminar, cliente no encontrado");
        }

        clientRepository.deleteById(id);
        log.info("Cliente id " + id + " eliminado correctamente");
    }
}
