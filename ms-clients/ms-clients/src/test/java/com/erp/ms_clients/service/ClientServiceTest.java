package com.erp.ms_clients.service;

import com.erp.ms_clients.dto.ClientDTO;
import com.erp.ms_clients.model.Client;
import com.erp.ms_clients.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    private Client clientEntity;
    private ClientDTO clientDto;

    @BeforeEach
    void setUp() {
        clientEntity = new Client();
        clientEntity.setId(1L);
        clientEntity.setRut("12345678-K"); 
        clientEntity.setNombre("Juan Perez");
        clientEntity.setEmail("juan@erp.com");
        clientEntity.setTelefono("+56912345678");
        clientEntity.setFechaCreacion(LocalDateTime.now());

        clientDto = new ClientDTO();
        clientDto.setRut("12.345.678-k "); 
        clientDto.setNombre("Juan Perez");
        clientDto.setEmail("juan@erp.com");
        clientDto.setTelefono("+56912345678");
    }

    @Test
    void getAll_DeberiaRetornarListaDeClientDTO() {
        when(clientRepository.findAll()).thenReturn(List.of(clientEntity));

        List<ClientDTO> resultado = clientService.getAll();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("12345678-K", resultado.get(0).getRut());
        verify(clientRepository, times(1)).findAll();
    }

    @Test
    void getById_CuandoIdExiste_DeberiaRetornarClientDTO() {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(clientEntity));

        ClientDTO resultado = clientService.getById(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Juan Perez", resultado.getNombre());
    }

    @Test
    void getById_CuandoIdNoExiste_DeberiaLanzarRuntimeException() {
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> clientService.getById(1L));
        assertEquals("Cliente no encontrado", exception.getMessage());
    }

    @Test
    void getByRut_DeberiaLimpiarElRutYRetornarCliente() {
        String rutSucio = "12.345.678-K ";
        String rutLimpioEsperado = "12345678-K";

        when(clientRepository.findByRut(rutLimpioEsperado)).thenReturn(Optional.of(clientEntity));

        ClientDTO resultado = clientService.getByRut(rutSucio);

        assertNotNull(resultado);
        assertEquals(rutLimpioEsperado, resultado.getRut());
    }

    @Test
    void create_CuandoRutNoExiste_DeberiaGuardarExitosamente() {
        String rutLimpioEsperado = "12345678-K";
        when(clientRepository.findByRut(rutLimpioEsperado)).thenReturn(Optional.empty());
        when(clientRepository.save(any(Client.class))).thenReturn(clientEntity);

        ClientDTO resultado = clientService.create(clientDto);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(rutLimpioEsperado, resultado.getRut());
        verify(clientRepository, times(1)).save(any(Client.class));
    }

    @Test
    void create_CuandoRutYaExiste_DeberiaLanzarRuntimeException() {
        String rutLimpioEsperado = "12345678-K";
        when(clientRepository.findByRut(rutLimpioEsperado)).thenReturn(Optional.of(clientEntity));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> clientService.create(clientDto));
        assertTrue(exception.getMessage().contains("ya existe en el sistema"));
        verify(clientRepository, never()).save(any(Client.class));
    }

    @Test
    void update_CuandoIdExiste_DeberiaActualizarYRetornarClientDTO() {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(clientEntity));
        when(clientRepository.save(any(Client.class))).thenReturn(clientEntity);
        clientDto.setNombre("Juan Actualizado");

        ClientDTO resultado = clientService.update(1L, clientDto);

        assertNotNull(resultado);
        verify(clientRepository, times(1)).save(any(Client.class));
    }

    @Test
    void delete_CuandoIdExiste_DeberiaEliminarCorrectamente() {
        when(clientRepository.existsById(1L)).thenReturn(true);
        doNothing().when(clientRepository).deleteById(1L);

        assertDoesNotThrow(() -> clientService.delete(1L));
        verify(clientRepository, times(1)).deleteById(1L);
    }

    @Test
    void delete_CuandoIdNoExiste_DeberiaLanzarRuntimeException() {
        when(clientRepository.existsById(1L)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> clientService.delete(1L));
        assertEquals("No se puede eliminar, cliente no encontrado", exception.getMessage());
        verify(clientRepository, never()).deleteById(anyLong());
    }
}