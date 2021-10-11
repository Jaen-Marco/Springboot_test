package org.jaen.springtest.springboot_test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.jaen.springtest.springboot_test.Datos.*;

import org.jaen.springtest.springboot_test.exceptions.DineroInsuficiente;
import org.jaen.springtest.springboot_test.models.Cuenta;
import org.jaen.springtest.springboot_test.repositories.BancoRepository;
import org.jaen.springtest.springboot_test.repositories.CuentaRepository;
import org.jaen.springtest.springboot_test.services.CuentaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;

@SpringBootTest
class SpringbootTestApplicationTests {

	@MockBean
	CuentaRepository cuentaRepository;
	@MockBean
	BancoRepository bancoRepository;

	@Autowired //Usar en la implementación @Service para poder conectar las inyecciones en el Test
	CuentaService service; //Detecta automáticamente que debe implementar el CuentaServiceImpl

	@BeforeEach
	void setUp(){
		when(cuentaRepository.findById(1L)).thenReturn(crearCuenta001());
		when(cuentaRepository.findById(2L)).thenReturn(crearCuenta002());
		when(bancoRepository.findById(1L)).thenReturn(crearBanco());
	}

	@Test
	void contextLoads() {
		service.transferir(1L, 2L, new BigDecimal("100"), 1L);

		BigDecimal saldoOrigen = service.verSaldo(1L);
		BigDecimal saldoDestino = service.verSaldo(2L);
		assertEquals("900", saldoOrigen.toPlainString());
		assertEquals("2100", saldoDestino.toPlainString());

		int total = service.revisarTransferencias(1L);
		assertEquals(1, total);

		verify(cuentaRepository, times(2)).findById(1L);
		verify(cuentaRepository, times(2)).findById(2L);
		verify(cuentaRepository,times(2)).save(any(Cuenta.class));
	}

	@Test
	void seeException() {

		assertThrows(DineroInsuficiente.class, ()-> {
			service.transferir(1L, 2L, new BigDecimal("1200"), 1L);
		});

		BigDecimal saldoOrigen = service.verSaldo(1L);
		BigDecimal saldoDestino = service.verSaldo(2L);
		assertEquals("1000", saldoOrigen.toPlainString());
		assertEquals("2000", saldoDestino.toPlainString());
	}
}
