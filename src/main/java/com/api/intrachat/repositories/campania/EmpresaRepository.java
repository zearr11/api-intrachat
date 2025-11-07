package com.api.intrachat.repositories.campania;

import com.api.intrachat.models.campania.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    Optional<Empresa> findByNombre(String nombre);

}
