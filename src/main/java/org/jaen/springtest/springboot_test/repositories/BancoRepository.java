package org.jaen.springtest.springboot_test.repositories;

import org.jaen.springtest.springboot_test.models.Banco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BancoRepository extends JpaRepository<Banco, Long> {
//    List<Banco> findAll();
//    Banco findById(Long id);
//    void update(Banco banco);
}
