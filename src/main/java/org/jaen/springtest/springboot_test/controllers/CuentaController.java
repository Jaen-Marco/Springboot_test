package org.jaen.springtest.springboot_test.controllers;

import org.jaen.springtest.springboot_test.models.TransaccionDto;
import org.springframework.http.HttpStatus;

import org.jaen.springtest.springboot_test.models.Cuenta;
import org.jaen.springtest.springboot_test.services.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Cuenta detalle(@PathVariable Long id){
        return cuentaService.findById(id);
    }

    @PostMapping("/transferir")
    public ResponseEntity<?> transferir(@RequestBody TransaccionDto dto){
        cuentaService.transferir(dto.getCuentaOrigenId(), dto.getCuentaDestinoId(),
                                dto.getMonto(), dto.getBancoId());
        Map<String, Object> response = new HashMap<>();
        response.put("date", LocalDate.now().toString());
        response.put("status", "OK");
        response.put("mensaje", "Trasnferencia realizada con éxito");
        response.put("transaccion", dto);

        return ResponseEntity.ok(response);
    }

}
