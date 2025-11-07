package com.api.intrachat.repositories.campania;

import com.api.intrachat.models.campania.Campania;
import com.api.intrachat.models.campania.Empresa;
import com.api.intrachat.models.campania.Sede;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CampaniaRepository extends JpaRepository<Campania, Long> {

    Optional<Campania> findByNombre(String nombre);
    List<Campania> findByEmpresa(Empresa empresa);

    @Query("SELECT c FROM Campania c JOIN c.campaniaSedes s WHERE s.id = :idSede")
    List<Campania> findBySedeId(@Param("idSede") Long idSede);

    Page<Campania> findByEstado(boolean estado, Pageable pageable);

}
