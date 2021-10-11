package org.jaen.springtest.springboot_test;

import org.jaen.springtest.springboot_test.models.Cuenta;
import org.jaen.springtest.springboot_test.repositories.CuentaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@DataJpaTest
public class IntegracionJpaTest {

    @Autowired
    CuentaRepository cuentaRepository;

    @Test
    void findByIdTest() {
        Optional<Cuenta> cuenta = cuentaRepository.findById(1L);
        assertTrue(cuenta.isPresent());
        assertEquals("Andres", cuenta.orElseThrow().getNombre());
    }

    @Test
    void findByNombreTest() {
        Optional<Cuenta> cuenta = cuentaRepository.findByNombre("Andres");
        assertTrue(cuenta.isPresent());
        assertEquals("Andres", cuenta.orElseThrow().getNombre());
        assertEquals("1000.00", cuenta.orElseThrow().getSaldo().toPlainString());
    }

    @Test
    void findByNombreErradoTest() {
        Optional<Cuenta> cuenta = cuentaRepository.findByNombre("Jose");
        assertThrows(NoSuchElementException.class, cuenta::orElseThrow);
        assertFalse(cuenta.isPresent());
    }

    @Test
    void findAllTest() {
        List<Cuenta> cuentas = cuentaRepository.findAll();
        assertFalse(cuentas.isEmpty());
        assertEquals(2, cuentas.size());
    }

    @Test
    void saveTest() {
        //Given
        Cuenta cuentaNueva = new Cuenta(null, "Jose", new BigDecimal("3000"));
        cuentaRepository.save(cuentaNueva);
        //When
        Cuenta cuenta = cuentaRepository.findByNombre("Jose").orElseThrow();
        //Then
        assertEquals("Jose", cuenta.getNombre());
        assertEquals(3, cuenta.getId()); //Validar si aún borrando en cascada, el ID sigue incrementando
    }

    @Test
    void updateTest() {
        Cuenta cuentaNueva = new Cuenta(null, "Jose", new BigDecimal("3000"));
        Cuenta cuenta = cuentaRepository.save(cuentaNueva);

        cuenta.setSaldo(new BigDecimal("4500"));
        Cuenta cuentaActualizada = cuentaRepository.save(cuenta);

        assertEquals("Jose", cuentaActualizada.getNombre());
        assertEquals("4500", cuentaActualizada.getSaldo().toPlainString());
    }

    @Test
    void deleteTest() {
        Cuenta cuenta = cuentaRepository.findById(2L).orElseThrow();
        assertEquals("Jhon", cuenta.getNombre());

        cuentaRepository.delete(cuenta);

        assertThrows(NoSuchElementException.class, ()->{
           cuentaRepository.findByNombre("Jhon").orElseThrow();
        });
    }
}
