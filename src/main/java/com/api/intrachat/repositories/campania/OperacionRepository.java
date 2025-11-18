package com.api.intrachat.repositories.campania;

import com.api.intrachat.models.campania.Operacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OperacionRepository extends JpaRepository<Operacion, Long> {

    @Query("""
        SELECT o FROM Operacion o
        WHERE (:estado IS NULL OR o.estado = :estado)
        AND (:idCampania IS NULL OR o.campania.id = :idCampania)
        AND (:idJefeOperacion IS NULL OR o.jefeOperacion.id = :idJefeOperacion)
    """)
    Page<Operacion> buscarOperacionesPaginado(
            @Param("estado") Boolean estado,
            @Param("idCampania") Long idCampania,
            @Param("idJefeOperacion") Long idJefeOperacion,
            Pageable pageable
    );

}
