package com.erp.ms_clients.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.erp.ms_clients.dto.ClientDTO;
import com.erp.ms_clients.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ClientControllerTest {

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController clientController;

    private ClientDTO clientDto;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();

        clientDto = new ClientDTO();
        clientDto.setId(1L);
        clientDto.setRut("12345678-K");
        clientDto.setNombre("Juan Perez");
        clientDto.setEmail("juan@erp.com");
        clientDto.setTelefono("+56912345678");
    }

    @Test
    void getAll_DeberiaRetornarStatusOkYLista() throws Exception {
        when(clientService.getAll()).thenReturn(List.of(clientDto));

        mockMvc.perform(get("/api/clients")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].rut").value("12345678-K"))
                .andExpect(jsonPath("$[0].nombre").value("Juan Perez"));

        verify(clientService, times(1)).getAll();
    }

    @Test
    void getById_CuandoIdExiste_DeberiaRetornarStatusOkYCliente() throws Exception {
        when(clientService.getById(1L)).thenReturn(clientDto);

        mockMvc.perform(get("/api/clients/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Juan Perez"));

        verify(clientService, times(1)).getById(1L);
    }

    @Test
    void getById_CuandoIdNoExiste_DeberiaLanzarExcepcion() {
        when(clientService.getById(1L)).thenThrow(new RuntimeException("Cliente no encontrado"));

        assertThrows(Exception.class, () -> {
            mockMvc.perform(get("/api/clients/1")
                    .contentType(MediaType.APPLICATION_JSON));
        });
    }

    @Test
    void getByRut_DeberiaRetornarStatusOkYCliente() throws Exception {
        when(clientService.getByRut("12345678-K")).thenReturn(clientDto);

        mockMvc.perform(get("/api/clients/rut/12345678-K")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rut").value("12345678-K"));

        verify(clientService, times(1)).getByRut("12345678-K");
    }

    @Test
    void create_DeberiaRetornarStatusCreatedYClienteGuardado() throws Exception {
        when(clientService.create(any(ClientDTO.class))).thenReturn(clientDto);

        mockMvc.perform(post("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.rut").value("12345678-K"));

        verify(clientService, times(1)).create(any(ClientDTO.class));
    }

    @Test
    void update_DeberiaRetornarStatusOkYClienteActualizado() throws Exception {
        when(clientService.update(eq(1L), any(ClientDTO.class))).thenReturn(clientDto);

        mockMvc.perform(put("/api/clients/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        verify(clientService, times(1)).update(eq(1L), any(ClientDTO.class));
    }

    @Test
    void delete_DeberiaRetornarStatusNoContent() throws Exception {
        doNothing().when(clientService).delete(1L);

        mockMvc.perform(delete("/api/clients/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(clientService, times(1)).delete(1L);
    }
}