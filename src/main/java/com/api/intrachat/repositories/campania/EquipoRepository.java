package com.api.intrachat.repositories.campania;

import com.api.intrachat.models.campania.Equipo;
import com.api.intrachat.models.campania.Operacion;
import com.api.intrachat.models.chat.Grupo;
import com.api.intrachat.repositories.campania.projections.EquipoProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EquipoRepository extends JpaRepository<Equipo, Long> {

    List<Equipo> findByOperacion(Operacion operacion);
    Optional<Equipo> findByGrupo(Grupo grupo);

    @Query(
            value = """
            SELECT
                eq.id AS idEquipo,
                s.nombre AS sede,
                ca.nombre AS campania,
                eq.fecha_creacion AS fechaCreacion,
                eq.fecha_cierre AS fechaCierre,
                CONCAT(p_jefe.nombres, ' ', p_jefe.apellidos) AS jefeOperacion,
                CONCAT(p_sup.nombres, ' ', p_sup.apellidos) AS supervisor,
                g.nombre AS nombreEquipo,

                (SELECT COUNT(*)
                 FROM equipo_usuarios eu
                 WHERE eu.fk_id_equipo = eq.id AND eu.estado = 1
                ) AS integrantesActivos,

                (SELECT COUNT(*)
                 FROM equipo_usuarios eu
                 WHERE eu.fk_id_equipo = eq.id AND eu.estado = 0
                ) AS integrantesInactivos,

                COALESCE(
                    (SELECT COUNT(*) / NULLIF(DATEDIFF(MAX(m.fecha_creacion), MIN(m.fecha_creacion)) + 1, 0)
                     FROM mensajes m
                     WHERE m.fk_id_sala = g.fk_id_sala
                    ), 0
                ) AS promedioMensajesDiarios

            FROM equipos eq
            JOIN operaciones o ON eq.fk_id_operacion = o.id
            JOIN sedes s ON o.fk_id_sede = s.id
            JOIN campanias ca ON o.fk_id_campania = ca.id
            JOIN grupos g ON eq.fk_id_grupo = g.id
            JOIN usuarios u_jefe ON o.fk_id_jefe_operacion = u_jefe.id
            JOIN personas p_jefe ON u_jefe.fk_id_persona = p_jefe.id
            JOIN usuarios u_sup ON eq.fk_id_supervisor = u_sup.id
            JOIN personas p_sup ON u_sup.fk_id_persona = p_sup.id

            WHERE 
                (
                 (:estadoEquipo = TRUE AND eq.fecha_cierre IS NULL) OR
                 (:estadoEquipo = FALSE AND eq.fecha_cierre IS NOT NULL)
                )
                AND (
                    :filtro IS NULL OR :filtro = '' OR
                    s.nombre LIKE CONCAT('%', :filtro, '%') OR
                    ca.nombre LIKE CONCAT('%', :filtro, '%') OR
                    g.nombre LIKE CONCAT('%', :filtro, '%') OR
                    CONCAT(p_jefe.nombres, ' ', p_jefe.apellidos)
                        LIKE CONCAT('%', :filtro, '%') OR
                    CONCAT(p_sup.nombres, ' ', p_sup.apellidos)
                        LIKE CONCAT('%', :filtro, '%')
                )
            """,
            countQuery = """
            SELECT COUNT(*)
            FROM equipos eq
            JOIN operaciones o ON eq.fk_id_operacion = o.id
            JOIN sedes s ON o.fk_id_sede = s.id
            JOIN campanias ca ON o.fk_id_campania = ca.id
            JOIN grupos g ON eq.fk_id_grupo = g.id
            JOIN usuarios u_jefe ON o.fk_id_jefe_operacion = u_jefe.id
            JOIN usuarios u_sup ON eq.fk_id_supervisor = u_sup.id

            WHERE
                (
                 (:estadoEquipo = TRUE AND eq.fecha_cierre IS NULL) OR
                 (:estadoEquipo = FALSE AND eq.fecha_cierre IS NOT NULL)
                )
                AND (
                    :filtro IS NULL OR :filtro = '' OR
                    s.nombre LIKE CONCAT('%', :filtro, '%') OR
                    ca.nombre LIKE CONCAT('%', :filtro, '%') OR
                    g.nombre LIKE CONCAT('%', :filtro, '%') OR
                    CONCAT(u_jefe.nombres, ' ', u_jefe.apellidos)
                        LIKE CONCAT('%', :filtro, '%') OR
                    CONCAT(u_sup.nombres, ' ', u_sup.apellidos)
                        LIKE CONCAT('%', :filtro, '%')
                )
            """,
            nativeQuery = true
    )
    Page<EquipoProjection> buscarEquiposConFiltro(
            @Param("filtro") String filtro,
            @Param("estadoEquipo") Boolean estadoEquipo,
            Pageable pageable
    );

}
