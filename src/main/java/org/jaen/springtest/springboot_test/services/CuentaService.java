package org.jaen.springtest.springboot_test.services;

import org.jaen.springtest.springboot_test.models.Cuenta;

import java.math.BigDecimal;

public interface CuentaService {

    Cuenta findById(Long id);

    int revisarTransferencias(Long bancoId);

    BigDecimal verSaldo(Long cuentaId);

    void transferir(Long origen, Long destino, BigDecimal monto, Long bancoId);
}
