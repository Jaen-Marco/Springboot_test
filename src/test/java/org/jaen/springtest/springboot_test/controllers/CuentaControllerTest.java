package org.jaen.springtest.springboot_test.controllers;

import static org.jaen.springtest.springboot_test.Datos.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jaen.springtest.springboot_test.models.TransaccionDto;
import org.jaen.springtest.springboot_test.services.CuentaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcResultMatchersDsl;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CuentaController.class)
class CuentaControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CuentaService cuentaService;

    ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    void detalleTest() throws Exception {
        when(cuentaService.findById(1L)).thenReturn(crearCuenta001().orElseThrow());

        mvc.perform(get("/api/cuentas/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nombre").value("Andres"))
                .andExpect(jsonPath("$.saldo").value("1000"));
    }

    @Test
    void transferirTest() throws Exception{

        TransaccionDto dto = new TransaccionDto(1L, 2L, new BigDecimal("1000"), 1L);

        mvc.perform(post("/api/cuentas/transferir").contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.mensaje").value("Trasnferencia realizada con éxito"))
                .andExpect(jsonPath("$.transaccion.cuentaOrigenId").value(dto.getCuentaOrigenId()));
    }
}