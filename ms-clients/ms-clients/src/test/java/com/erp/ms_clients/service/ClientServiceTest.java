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

// Importaciones estáticas: permiten usar métodos como assertEquals() o when() directamente sin escribir "Assertions." o "Mockito."
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Habilita el ciclo de vida de Mockito en JUnit 5 para procesar las anotaciones @Mock y @InjectMocks
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository; // Crea un cascarón falso (Mock) del repositorio para no tocar la base de datos real

    @InjectMocks
    private ClientService clientService; // Crea la instancia real del servicio e inyecta automáticamente el 'clientRepository' falso dentro de él

    // Variables globales para los datos de prueba (Fixtures)
    private Client clientEntity;
    private ClientDTO clientDto;

    @BeforeEach
    void setUp() {
        // Se ejecuta antes de CADA método de prueba. Asegura que cada test empiece con datos limpios y frescos.
        
        // Inicialización de la Entidad de Base de Datos (Simula lo que vendría de JPA)
        clientEntity = new Client();
        clientEntity.setId(1L);
        clientEntity.setRut("12345678-K"); // Formato ya procesado/limpio
        clientEntity.setNombre("Juan Perez");
        clientEntity.setEmail("juan@erp.com");
        clientEntity.setTelefono("+56912345678");
        clientEntity.setFechaCreacion(LocalDateTime.now());

        // Inicialización del DTO (Simula lo que envía el usuario desde el Frontend/Postman)
        clientDto = new ClientDTO();
        clientDto.setRut("12.345.678-k "); // Viene con puntos, guion, espacios y minúscula (Formato sucio)
        clientDto.setNombre("Juan Perez");
        clientDto.setEmail("juan@erp.com");
        clientDto.setTelefono("+56912345678");
    }

    @Test
    void getAll_DeberiaRetornarListaDeClientDTO() {
        // Arrange: Cuando el servicio llame a findAll(), el mock devolverá una lista con nuestra entidad falsa
        when(clientRepository.findAll()).thenReturn(List.of(clientEntity));

        // Act: Se ejecuta el método real del servicio
        List<ClientDTO> resultado = clientService.getAll();

        // Assert: Validaciones
        assertNotNull(resultado); // Comprueba que no sea nulo
        assertEquals(1, resultado.size()); // Comprueba que mapeó y devolvió un registro
        assertEquals("12345678-K", resultado.get(0).getRut()); // Verifica el mapeo correcto
        verify(clientRepository, times(1)).findAll(); // Verifica que el repositorio se usó exactamente una vez
    }

    @Test
    void getById_CuandoIdExiste_DeberiaRetornarClientDTO() {
        // Arrange: Simula que la base de datos encuentra al cliente mediante un Optional
        when(clientRepository.findById(1L)).thenReturn(Optional.of(clientEntity));

        // Act
        ClientDTO resultado = clientService.getById(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Juan Perez", resultado.getNombre());
    }

    @Test
    void getById_CuandoIdNoExiste_DeberiaLanzarRuntimeException() {
        // Arrange: Simula que el repositorio no encuentra nada (Optional vacío)
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert (Combinados): Verifica que al ejecutar el método se lance una RuntimeException
        RuntimeException exception = assertThrows(RuntimeException.class, () -> clientService.getById(1L));
        
        // Verifica que el mensaje de error de la excepción sea el esperado
        assertEquals("Cliente no encontrado", exception.getMessage());
    }

    @Test
    void getByRut_DeberiaLimpiarElRutYRetornarCliente() {
        String rutSucio = "12.345.678-K ";
        String rutLimpioEsperado = "12345678-K";

        // Arrange: El comportamiento del mock espera el RUT ya formateado y limpio
        when(clientRepository.findByRut(rutLimpioEsperado)).thenReturn(Optional.of(clientEntity));

        // Act: Le pasamos el formato sucio al servicio
        ClientDTO resultado = clientService.getByRut(rutSucio);

        // Assert: Si el test pasa, demuestra que el servicio internamente limpió el string antes de consultar al repo
        assertNotNull(resultado);
        assertEquals(rutLimpioEsperado, resultado.getRut());
    }

    @Test
    void create_CuandoRutNoExiste_DeberiaGuardarExitosamente() {
        String rutLimpioEsperado = "12345678-K";
        // Arrange: 1. Comprueba que no exista el RUT. 2. Simula la acción de guardar devolviendo la entidad persistida.
        when(clientRepository.findByRut(rutLimpioEsperado)).thenReturn(Optional.empty());
        when(clientRepository.save(any(Client.class))).thenReturn(clientEntity); // any(Client.class) acepta cualquier objeto de tipo Client

        // Act
        ClientDTO resultado = clientService.create(clientDto);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(rutLimpioEsperado, resultado.getRut());
        verify(clientRepository, times(1)).save(any(Client.class)); // Confirma que se persistió
    }

    @Test
    void create_CuandoRutYaExiste_DeberiaLanzarRuntimeException() {
        String rutLimpioEsperado = "12345678-K";
        // Arrange: Simula que el RUT ya está tomado en la base de datos
        when(clientRepository.findByRut(rutLimpioEsperado)).thenReturn(Optional.of(clientEntity));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> clientService.create(clientDto));
        assertTrue(exception.getMessage().contains("ya existe en el sistema"));
        
        // Seguridad Crítica: Verifica que el repositorio JAMÁS intentó guardar el cliente repetido
        verify(clientRepository, never()).save(any(Client.class));
    }

    @Test
    void update_CuandoIdExiste_DeberiaActualizarYRetornarClientDTO() {
        // Arrange
        when(clientRepository.findById(1L)).thenReturn(Optional.of(clientEntity));
        when(clientRepository.save(any(Client.class))).thenReturn(clientEntity);
        clientDto.setNombre("Juan Actualizado");

        // Act
        ClientDTO resultado = clientService.update(1L, clientDto);

        // Assert
        assertNotNull(resultado);
        verify(clientRepository, times(1)).save(any(Client.class));
    }

    @Test
    void delete_CuandoIdExiste_DeberiaEliminarCorrectamente() {
        // Arrange
        when(clientRepository.existsById(1L)).thenReturn(true);
        // doNothing() se usa para métodos 'void' en Mockito cuando no queremos que hagan nada al ser llamados
        doNothing().when(clientRepository).deleteById(1L);

        // Act & Assert: Verifica que la ejecución no rompa ni lance ninguna excepción no controlada
        assertDoesNotThrow(() -> clientService.delete(1L));
        verify(clientRepository, times(1)).deleteById(1L);
    }

    @Test
    void delete_CuandoIdNoExiste_DeberiaLanzarRuntimeException() {
        // Arrange
        when(clientRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> clientService.delete(1L));
        assertEquals("No se puede eliminar, cliente no encontrado", exception.getMessage());
        
        // Verifica que si no existía el ID, nunca se llamó al método deleteById del repositorio (Evita llamadas innecesarias)
        verify(clientRepository, never()).deleteById(anyLong());
    }
}