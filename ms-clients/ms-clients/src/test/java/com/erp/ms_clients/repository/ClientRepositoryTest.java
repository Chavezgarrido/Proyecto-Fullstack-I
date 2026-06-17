package com.erp.ms_clients.repository;

import com.erp.ms_clients.model.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest // Configura Spring para levantar SOLO los componentes de JPA (Entidades, Repositorios, DataSource). Ignora controladores y servicios para ir rápido.
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY) // Reemplaza la base de datos real (ej. PostgreSQL/MySQL) por una base de datos de pruebas (generalmente H2 en memoria)
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop", // Genera las tablas al iniciar los tests y las destruye al finalizar la clase
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect" // Le dice a Hibernate que traduzca las consultas al dialecto de la base de datos H2
})
class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository; // Inyecta el repositorio real generado por Spring Data JPA que queremos testear

    @Autowired
    private TestEntityManager entityManager; // Una alternativa segura al EntityManager estándar, diseñada específicamente para usarse en pruebas de JPA

    private Client clientEntity;

    @BeforeEach
    void setUp() {
        // Inicializa un objeto limpio antes de cada test. Nota: Este objeto aún no tiene ID porque no se ha guardado en la BD.
        clientEntity = new Client();
        clientEntity.setRut("12345678-K");
        clientEntity.setNombre("Juan Perez");
        clientEntity.setEmail("juan@erp.com");
        clientEntity.setTelefono("+56912345678");
        clientEntity.setFechaCreacion(LocalDateTime.now());
    }

    @Test
    void findByRut_CuandoRutExiste_DeberiaRetornarCliente() {
        // Arrange: Guarda el cliente directamente en la BD en memoria y fuerza un 'flush' (sincronizar inmediatamente con las tablas)
        entityManager.persistAndFlush(clientEntity);

        // Act: Llama al método personalizado que creaste en tu interfaz 'ClientRepository'
        Optional<Client> resultado = clientRepository.findByRut("12345678-K");

        // Assert: Verifica si la consulta SQL generada por Spring Data JPA realmente encuentra el registro y mapea bien los campos
        assertTrue(resultado.isPresent());
        assertEquals("Juan Perez", resultado.get().getNombre());
        assertEquals("juan@erp.com", resultado.get().getEmail());
    }

    @Test
    void findByRut_CuandoRutNoExiste_DeberiaRetornarOptionalVacio() {
        // Act: Intenta buscar un RUT que nunca insertamos en la BD
        Optional<Client> resultado = clientRepository.findByRut("99999999-9");

        // Assert: Comprueba que el repositorio maneje correctamente la ausencia del registro devolviendo un Optional vacío
        assertTrue(resultado.isEmpty());
    }

    @Test
    void findByEmail_CuandoEmailExiste_DeberiaRetornarCliente() {
        // Arrange
        entityManager.persistAndFlush(clientEntity);

        // Act: Prueba otro método de consulta derivado (Query Method)
        Optional<Client> resultado = clientRepository.findByEmail("juan@erp.com");

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("12345678-K", resultado.get().getRut());
    }

    @Test
    void findByEmail_CuandoEmailNoExiste_DeberiaRetornarOptionalVacio() {
        // Act
        Optional<Client> resultado = clientRepository.findByEmail("noexiste@erp.com");

        // Assert
        assertTrue(resultado.isEmpty());
    }

    @Test
    void onCreate_DeberiaAsignarFechasAutomaticamenteAlPersistir() {
        // Arrange: Forzamos a que las fechas vayan nulas (Simula cuando creas un cliente nuevo desde el formulario de registro)
        clientEntity.setFechaCreacion(null);
        clientEntity.setFechaActualizacion(null);

        assertNull(clientEntity.getFechaCreacion());
        assertNull(clientEntity.getFechaActualizacion());

        // Act: Al persistir en la BD, se deberían disparar los listeners del ciclo de vida de JPA (como @PrePersist o @CreatedDate)
        Client clienteGuardado = entityManager.persistAndFlush(clientEntity);

        // Assert: Demuestra que la entidad real (en el código de producción) tiene lógica de auditoría automatizada
        assertNotNull(clienteGuardado.getFechaCreacion());
        assertNotNull(clienteGuardado.getFechaActualizacion());
    }

    @Test
    void onUpdate_DeberiaActualizarFechaActualizacionAlModificar() throws InterruptedException {
        // Arrange: Guardamos el cliente inicialmente para que se generen las primeras fechas
        Client clienteGuardado = entityManager.persistAndFlush(clientEntity);
        LocalDateTime fechaCreacionOriginal = clienteGuardado.getFechaCreacion();
        LocalDateTime fechaActualizacionOriginal = clienteGuardado.getFechaActualizacion();

        // Truco Técnico: Pausa el hilo 5 milisegundos para garantizar que, cuando cambie el tiempo, el reloj del sistema haya avanzado
        Thread.sleep(5);

        // Act: Modificamos un campo del cliente y volvemos a sincronizar con la base de datos (Simula una actualización)
        clienteGuardado.setNombre("Juan Modificado");
        Client clienteActualizado = entityManager.persistAndFlush(clienteGuardado);

        // Assert
        assertEquals(fechaCreacionOriginal, clienteActualizado.getFechaCreacion()); // La fecha de creación debe mantenerse INTACTA
        assertTrue(clienteActualizado.getFechaActualizacion().isAfter(fechaActualizacionOriginal)); // La fecha de actualización DEBE ser mayor (más reciente) que la original
    }
}