package com.erp.ms_clients.service;

import com.erp.ms_clients.model.Cliente;
import com.erp.ms_clients.repository.ClienteRepository;

import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Transactional(readOnly = true)
    public List<Cliente> obtenerTodos() {
        return clienteRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Cliente obtenerPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente id " + id + " no encontrado."));
    }

    @Transactional(readOnly = true)
    public Cliente buscarPorRut(String rut){
        return clienteRepository.findByRut(rut)
        .orElseThrow(() -> new RuntimeException("Cliente con rut " + rut + " no encontrado."));
    }

    @Transactional
    public Cliente crear(Cliente cliente){
        //limpiamos espacios en blanco o puntos con replace
        String rutLimpio = cliente.getRut().replace(".", "").replace(" ", "").toUpperCase();
        cliente.setRut(rutLimpio);

        //se valida que no exista en bbdd un cliente con ese rut
        if(clienteRepository.findByRut(cliente.getRut()).isPresent()){
            throw new RuntimeException("El rut " + cliente.getRut() + " ya está ingresado.");
        }
        //si no existe, se guarda el cliente
        return clienteRepository.save(cliente);
    }

    @Transactional
    public void eliminar(String rut){
        //reusamos la logica del método de mas arriba
        Cliente cliente = buscarPorRut(rut);
        clienteRepository.delete(cliente);
    }





}
