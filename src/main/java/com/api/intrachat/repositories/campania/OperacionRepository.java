package com.api.intrachat.repositories.campania;

import com.api.intrachat.models.campania.Operacion;
import com.api.intrachat.repositories.campania.projections.OperacionProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OperacionRepository extends JpaRepository<Operacion, Long> {

    @Query(
            value = """
                    SELECT
                        o.id AS id,
                        camp.nombre AS campania,
                        emp.nombre_comercial AS empresa,
                        s.nombre AS sede,
                        CONCAT(per.nombres, ' ', per.apellidos) AS jefeOperacion,
                    
                        (SELECT COUNT(*) FROM equipos eq WHERE eq.fk_id_operacion = o.id AND eq.fecha_cierre IS NULL)
                            AS totalEquiposOperativos,
                    
                        (SELECT COUNT(*) FROM equipos eq WHERE eq.fk_id_operacion = o.id AND eq.fecha_cierre IS NOT NULL)
                            AS totalEquiposInoperativos,
                    
                        (
                            SELECT COUNT(*)
                            FROM equipo_usuarios eu
                            JOIN equipos eq ON eu.fk_id_equipo = eq.id
                            WHERE eq.fk_id_operacion = o.id AND eu.estado = 1
                        ) AS totalUsuariosOperativos,
                    
                        (
                            SELECT COUNT(*)
                            FROM equipo_usuarios eu
                            JOIN equipos eq ON eu.fk_id_equipo = eq.id
                            WHERE eq.fk_id_operacion = o.id AND eu.estado = 0
                        ) AS totalUsuariosInoperativos,
                    
                        o.fecha_creacion AS fechaCreacion,
                        o.fecha_finalizacion AS fechaFinalizacion
                    
                    FROM operaciones o
                    JOIN campanias camp ON o.fk_id_campania = camp.id
                    JOIN empresas emp ON camp.fk_id_empresa = emp.id
                    JOIN usuarios u ON o.fk_id_jefe_operacion = u.id
                    JOIN personas per ON u.fk_id_persona = per.id
                    JOIN sedes s ON o.fk_id_sede = s.id
                    
                    WHERE ( :idCampania IS NULL OR camp.id = :idCampania )
                      AND (
                            (:mostrarActivas = 1 AND o.fecha_finalizacion IS NULL)
                         OR (:mostrarActivas = 0 AND o.fecha_finalizacion IS NOT NULL)
                          )
                      AND (
                            :filtro IS NULL OR :filtro = '' OR
                            LOWER(camp.nombre) LIKE LOWER(CONCAT('%', :filtro, '%')) OR
                            LOWER(emp.nombre_comercial) LIKE LOWER(CONCAT('%', :filtro, '%')) OR
                            LOWER(s.nombre) LIKE LOWER(CONCAT('%', :filtro, '%')) OR
                            LOWER(CONCAT(per.nombres, ' ', per.apellidos)) LIKE LOWER(CONCAT('%', :filtro, '%'))
                          )
                    """,
            countQuery = """
                    SELECT COUNT(*)
                    FROM operaciones o
                    JOIN campanias camp ON o.fk_id_campania = camp.id
                    WHERE ( :idCampania IS NULL OR camp.id = :idCampania )
                      AND (
                            (:mostrarActivas = 1 AND o.fecha_finalizacion IS NULL)
                         OR (:mostrarActivas = 0 AND o.fecha_finalizacion IS NOT NULL)
                          )
                      AND (
                            :filtro IS NULL OR :filtro = '' OR
                            LOWER(camp.nombre) LIKE LOWER(CONCAT('%', :filtro, '%')) OR
                            LOWER(emp.nombre_comercial) LIKE LOWER(CONCAT('%', :filtro, '%')) OR
                            LOWER(s.nombre) LIKE LOWER(CONCAT('%', :filtro, '%')) OR
                            LOWER(CONCAT(per.nombres, ' ', per.apellidos)) LIKE LOWER(CONCAT('%', :filtro, '%'))
                          )
                    """,
            nativeQuery = true
    )
    Page<OperacionProjection> buscarOperacionesPorCampaniaYEstado(
            @Param("idCampania") Long idCampania,
            @Param("mostrarActivas") Integer mostrarActivas,
            @Param("filtro") String filtro,
            Pageable pageable
    );

}
