package org.jaen.springtest.springboot_test.services;

import org.jaen.springtest.springboot_test.models.Banco;
import org.jaen.springtest.springboot_test.models.Cuenta;
import org.jaen.springtest.springboot_test.repositories.BancoRepository;
import org.jaen.springtest.springboot_test.repositories.CuentaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CuentaServiceImpl implements CuentaService {

    private CuentaRepository cuentaRepository;
    private BancoRepository bancoRepository;

    public CuentaServiceImpl(CuentaRepository cuentaRepository, BancoRepository bancoRepository) {
        this.cuentaRepository = cuentaRepository;
        this.bancoRepository = bancoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cuenta> findAll() { return cuentaRepository.findAll(); }

    @Override
    @Transactional(readOnly = true)
    public Cuenta findById(Long id) {
        return cuentaRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional
    public Cuenta save(Cuenta cuenta) { return cuentaRepository.save(cuenta); }

    @Override
    @Transactional(readOnly = true)
    public int revisarTransferencias(Long bancoId) {
        Banco banco = bancoRepository.findById(bancoId).orElseThrow();
        return banco.getTotalTransferencia();
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal verSaldo(Long cuentaId) {
        Cuenta cuenta = cuentaRepository.findById(cuentaId).orElseThrow();
        return cuenta.getSaldo();
    }

    @Override
    @Transactional
    public void transferir(Long origen, Long destino, BigDecimal monto,
                           Long bancoId) {
        Cuenta cuentaOrigen = cuentaRepository.findById(origen).orElseThrow();
        cuentaOrigen.debito(monto);
        cuentaRepository.save(cuentaOrigen);

        Cuenta cuentaDestino = cuentaRepository.findById(destino).orElseThrow();
        cuentaDestino.credito(monto);
        cuentaRepository.save(cuentaDestino);

        Banco banco = bancoRepository.findById(bancoId).orElseThrow();
        int totalTransferencia = banco.getTotalTransferencia();
        banco.setTotalTransferencia(++totalTransferencia);
        bancoRepository.save(banco);
    }
}
