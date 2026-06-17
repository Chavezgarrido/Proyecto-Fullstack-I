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
// Importaciones estáticas vitales para simular peticiones HTTP (get, post) y verificar resultados (status, jsonPath)
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class) // Inicializa el entorno con soporte de Mockito
class ClientControllerTest {

    private MockMvc mockMvc; // El motor principal del test. Permite "lanzar" peticiones HTTP simuladas contra el controlador.

    // Herramienta de Jackson para convertir objetos Java a Strings JSON (Serialización) y viceversa.
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private ClientService clientService; // El servicio falso que el controlador necesita consumir

    @InjectMocks
    private ClientController clientController; // El controlador real donde se inyectará el servicio mockeado

    private ClientDTO clientDto;

    @BeforeEach
    void setUp() {
        // Truco Clave: Inicializa MockMvc enfocándose ÚNICAMENTE en este controlador de forma aislada (Standalone)
        mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();

        // Fixture: Preparación del DTO que simulará viajar por la red HTTP
        clientDto = new ClientDTO();
        clientDto.setId(1L);
        clientDto.setRut("12345678-K");
        clientDto.setNombre("Juan Perez");
        clientDto.setEmail("juan@erp.com");
        clientDto.setTelefono("+56912345678");
    }

    @Test
    void getAll_DeberiaRetornarStatusOkYLista() throws Exception {
        // Arrange: Cuando el controlador pida los datos al servicio, este devolverá la lista mockeada
        when(clientService.getAll()).thenReturn(List.of(clientDto));

        // Act & Assert (Encadenados mediante la API fluida de MockMvc)
        mockMvc.perform(get("/api/clients") // Ejecuta una petición HTTP GET a esa URL exacta
                .contentType(MediaType.APPLICATION_JSON)) // Setea las cabeceras HTTP de la petición
                .andExpect(status().isOk()) // Verifica que el HTTP Status sea 200 (OK)
                // Usamos expresiones JsonPath ($ representa la raíz del JSON, que en este caso es una lista [])
                .andExpect(jsonPath("$[0].id").value(1L)) 
                .andExpect(jsonPath("$[0].rut").value("12345678-K"))
                .andExpect(jsonPath("$[0].nombre").value("Juan Perez"));

        verify(clientService, times(1)).getAll(); // Valida que el controlador delegó el trabajo al servicio
    }

    @Test
    void getById_CuandoIdExiste_DeberiaRetornarStatusOkYCliente() throws Exception {
        // Arrange
        when(clientService.getById(1L)).thenReturn(clientDto);

        // Act & Assert
        mockMvc.perform(get("/api/clients/1") // Envía la variable de ruta (PathVariable) id = 1
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                // Al ser un solo objeto (no una lista), accedemos directo a las propiedades con $.propiedad
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Juan Perez"));

        verify(clientService, times(1)).getById(1L);
    }

    @Test
    void getById_CuandoIdNoExiste_DeberiaLanzarExcepcion() {
        // Arrange: Programamos el mock para que dispare un error cuando el controlador le pida el ID 1
        when(clientService.getById(1L)).thenThrow(new RuntimeException("Cliente no encontrado"));

        // Act & Assert
        // Al estar en un entorno 'standalone' sin manejador de excepciones global, el error burbujea directo.
        // Verificamos que el lanzamiento de la petición lance efectivamente una excepción de Java.
        assertThrows(Exception.class, () -> {
            mockMvc.perform(get("/api/clients/1")
                    .contentType(MediaType.APPLICATION_JSON));
        });
    }

    @Test
    void getByRut_DeberiaRetornarStatusOkYCliente() throws Exception {
        // Arrange
        when(clientService.getByRut("12345678-K")).thenReturn(clientDto);

        // Act & Assert
        mockMvc.perform(get("/api/clients/rut/12345678-K"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rut").value("12345678-K"));

        verify(clientService, times(1)).getByRut("12345678-K");
    }

    @Test
    void create_DeberiaRetornarStatusCreatedYClienteGuardado() throws Exception {
        // Arrange: any(ClientDTO.class) actúa como comodín para cualquier DTO entrante
        when(clientService.create(any(ClientDTO.class))).thenReturn(clientDto);

        // Act & Assert
        mockMvc.perform(post("/api/clients") // Cambiamos el verbo HTTP a POST
                .contentType(MediaType.APPLICATION_JSON)
                // .content() requiere un String. objectMapper convierte nuestro DTO de Java a un String JSON válido ("{...}")
                .content(objectMapper.writeValueAsString(clientDto))) 
                .andExpect(status().isCreated()) // Verifica que el HTTP Status sea 201 (CREATED)
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.rut").value("12345678-K"));

        verify(clientService, times(1)).create(any(ClientDTO.class));
    }

    @Test
    void update_DeberiaRetornarStatusOkYClienteActualizado() throws Exception {
        // Arrange: eq(1L) asegura que el primer parámetro sea exactamente el ID 1, combinándolo con any() para el DTO
        when(clientService.update(eq(1L), any(ClientDTO.class))).thenReturn(clientDto);

        // Act & Assert
        mockMvc.perform(put("/api/clients/1") // Cambiamos el verbo HTTP a PUT
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        verify(clientService, times(1)).update(eq(1L), any(ClientDTO.class));
    }

    @Test
    void delete_DeberiaRetornarStatusNoContent() throws Exception {
        // Arrange: doNothing() es el estándar de Mockito para indicar que un método 'void' se ejecutará sin errores
        doNothing().when(clientService).delete(1L);

        // Act & Assert
        mockMvc.perform(delete("/api/clients/1") // Cambiamos el verbo HTTP a DELETE
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent()); // Verifica que el HTTP Status sea 204 (NO CONTENT)

        verify(clientService, times(1)).delete(1L);
    }
}