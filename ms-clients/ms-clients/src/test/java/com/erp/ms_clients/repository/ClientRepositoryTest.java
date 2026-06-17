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

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect"
})
class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Client clientEntity;

    @BeforeEach
    void setUp() {
        clientEntity = new Client();
        clientEntity.setRut("12345678-K");
        clientEntity.setNombre("Juan Perez");
        clientEntity.setEmail("juan@erp.com");
        clientEntity.setTelefono("+56912345678");
        clientEntity.setFechaCreacion(LocalDateTime.now());
    }

    @Test
    void findByRut_CuandoRutExiste_DeberiaRetornarCliente() {
        entityManager.persistAndFlush(clientEntity);

        Optional<Client> resultado = clientRepository.findByRut("12345678-K");

        assertTrue(resultado.isPresent());
        assertEquals("Juan Perez", resultado.get().getNombre());
        assertEquals("juan@erp.com", resultado.get().getEmail());
    }

    @Test
    void findByRut_CuandoRutNoExiste_DeberiaRetornarOptionalVacio() {
        Optional<Client> resultado = clientRepository.findByRut("99999999-9");

        assertTrue(resultado.isEmpty());
    }

    @Test
    void findByEmail_CuandoEmailExiste_DeberiaRetornarCliente() {
        entityManager.persistAndFlush(clientEntity);

        Optional<Client> resultado = clientRepository.findByEmail("juan@erp.com");

        assertTrue(resultado.isPresent());
        assertEquals("12345678-K", resultado.get().getRut());
    }

    @Test
    void findByEmail_CuandoEmailNoExiste_DeberiaRetornarOptionalVacio() {
        Optional<Client> resultado = clientRepository.findByEmail("noexiste@erp.com");

        assertTrue(resultado.isEmpty());
    }

    @Test
    void onCreate_DeberiaAsignarFechasAutomaticamenteAlPersistir() {
        clientEntity.setFechaCreacion(null);
        clientEntity.setFechaActualizacion(null);

        assertNull(clientEntity.getFechaCreacion());
        assertNull(clientEntity.getFechaActualizacion());

        Client clienteGuardado = entityManager.persistAndFlush(clientEntity);

        assertNotNull(clienteGuardado.getFechaCreacion());
        assertNotNull(clienteGuardado.getFechaActualizacion());
    }

    @Test
    void onUpdate_DeberiaActualizarFechaActualizacionAlModificar() throws InterruptedException {
        Client clienteGuardado = entityManager.persistAndFlush(clientEntity);
        LocalDateTime fechaCreacionOriginal = clienteGuardado.getFechaCreacion();
        LocalDateTime fechaActualizacionOriginal = clienteGuardado.getFechaActualizacion();

        Thread.sleep(5);

        clienteGuardado.setNombre("Juan Modificado");
        Client clienteActualizado = entityManager.persistAndFlush(clienteGuardado);

        assertEquals(fechaCreacionOriginal, clienteActualizado.getFechaCreacion());
        assertTrue(clienteActualizado.getFechaActualizacion().isAfter(fechaActualizacionOriginal));
    }
}