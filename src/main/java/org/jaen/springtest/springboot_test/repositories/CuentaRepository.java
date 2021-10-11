package org.jaen.springtest.springboot_test.repositories;

import org.jaen.springtest.springboot_test.models.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

//AL extender de JpaRepository ya posee metodos de CrudRepository. Ejm: findById, save, findAll, etc.
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
    @Query("select c from Cuenta c where c.nombre=?1")
    Optional<Cuenta> findByNombre(String nombre);

//    List<Cuenta> findAll();
//    Cuenta findById(Long id);
//    void update(Cuenta cuenta);
}
