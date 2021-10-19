package org.jaen.springtest.springboot_test.services;

import org.jaen.springtest.springboot_test.models.Cuenta;

import java.math.BigDecimal;
import java.util.List;

public interface CuentaService {

    List<Cuenta> findAll();

    Cuenta findById(Long id);

    Cuenta save(Cuenta cuenta);

    int revisarTransferencias(Long bancoId);

    BigDecimal verSaldo(Long cuentaId);

    void transferir(Long origen, Long destino, BigDecimal monto, Long bancoId);
}
