package com.api.intrachat.repositories.general;

import com.api.intrachat.models.campania.Campania;
import com.api.intrachat.models.general.Persona;
import com.api.intrachat.models.general.Usuario;
import com.api.intrachat.repositories.general.projections.UsuariosPorMesProjection;
import com.api.intrachat.utils.enums.Cargo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("""
                SELECT u FROM Usuario u
                JOIN u.persona p
                WHERE (:estado IS NULL OR u.estado = :estado)
                AND (:idUsuarioActual IS NULL OR u.id <> :idUsuarioActual)
                AND (:cargo IS NULL OR u.cargo = :cargo)
            
                AND (
                    :enCampania IS NULL OR
                    (
                        :enCampania = TRUE AND EXISTS (
                            SELECT 1 FROM EquipoUsuarios eu
                            WHERE eu.usuario.id = u.id
                            AND eu.estado = TRUE
                        )
                    ) OR (
                        :enCampania = FALSE AND NOT EXISTS (
                            SELECT 1 FROM EquipoUsuarios eu
                            WHERE eu.usuario.id = u.id
                            AND eu.estado = TRUE
                        )
                    )
                )
            
                AND (
                    :filtro IS NULL OR :filtro = '' OR
                    LOWER(CONCAT(p.nombres, ' ', p.apellidos)) LIKE LOWER(CONCAT('%', :filtro, '%')) OR
                    LOWER(p.numeroDoc) LIKE LOWER(CONCAT('%', :filtro, '%')) OR
                    LOWER(u.email) LIKE LOWER(CONCAT('%', :filtro, '%')) OR
                    LOWER(p.celular) LIKE LOWER(CONCAT('%', :filtro, '%')) OR
                    LOWER(CAST(u.cargo AS string)) LIKE LOWER(CONCAT('%', :filtro, '%'))
                )
            """)
    Page<Usuario> buscarUsuariosConPaginacion(
            @Param("estado") Boolean estado,
            @Param("idUsuarioActual") Long idUsuarioActual,
            @Param("filtro") String filtro,
            @Param("cargo") Cargo cargo,
            @Param("enCampania") Boolean enCampania,
            Pageable pageable
    );

    @Query("""
                SELECT u FROM Usuario u
                JOIN u.persona p
                WHERE (:cargo IS NULL OR u.cargo = :cargo)
                  AND (:estado IS NULL OR u.estado = :estado)
                  AND (
                      :filtro IS NULL OR :filtro = '' OR
                      LOWER(CONCAT(p.nombres, ' ', p.apellidos)) LIKE LOWER(CONCAT('%', :filtro, '%')) OR
                      LOWER(p.numeroDoc) LIKE LOWER(CONCAT('%', :filtro, '%')) OR
                      LOWER(u.email) LIKE LOWER(CONCAT('%', :filtro, '%')) OR
                      LOWER(p.celular) LIKE LOWER(CONCAT('%', :filtro, '%')) OR
                      LOWER(u.cargo) LIKE LOWER(CONCAT('%', :filtro, '%'))
                  )
                  AND u.id <> :idUsuarioActual
                  AND (
                      :idEquipo IS NULL
                      OR (
                          EXISTS (
                              SELECT 1 FROM EquipoUsuarios euEq
                              WHERE euEq.usuario.id = u.id
                                AND euEq.equipo.id = :idEquipo
                                AND euEq.estado = true
                          )
            
                          OR
            
                          NOT EXISTS (
                              SELECT 1 FROM EquipoUsuarios euAny
                              WHERE euAny.usuario.id = u.id
                                AND euAny.estado = true
                          )
                      )
                  )
            """)
    Page<Usuario> buscarUsuariosDeEquipoYsinCampania(
            @Param("estado") Boolean estado,
            @Param("cargo") Cargo cargo,
            @Param("filtro") String filtro,
            @Param("idUsuarioActual") Long idUsuarioActual,
            @Param("idEquipo") Long idEquipo,
            Pageable pageable
    );

    Optional<Usuario> findByEmail(String email);

    Usuario findByPersona(Persona persona);

    @Query("""
                SELECT DISTINCT u FROM Usuario u
                JOIN EquipoUsuarios eu ON eu.usuario = u
                JOIN Equipo e ON eu.equipo = e
                JOIN Operacion o ON e.operacion = o
                JOIN Campania c ON o.campania = c
                WHERE c.id = :idCampania
                  AND u.id <> :idUsuarioExcluir
                  AND u.estado = true
                  AND eu.estado = true
                  AND e.fechaCierre IS NULL
                  AND o.fechaFinalizacion IS NULL
                  AND c.estado = true
                  AND LOWER(CONCAT(u.persona.nombres, ' ', u.persona.apellidos))
                      LIKE LOWER(CONCAT('%', :filtro, '%'))
            """)
    List<Usuario> buscarContactosPorCampania(
            @Param("filtro") String filtro,
            @Param("idCampania") Long idCampania,
            @Param("idUsuarioExcluir") Long idUsuarioExcluir
    );

    @Query(
            value = """
                    SELECT us.*
                    FROM usuarios us
                    LEFT JOIN personas p ON us.fk_id_persona = p.id
                    WHERE 
                        us.estado = TRUE
                        AND us.cargo = 'JEFE_DE_OPERACION'
                        AND NOT EXISTS (
                            SELECT 1
                            FROM operaciones op2
                            WHERE op2.fk_id_jefe_operacion = us.id
                            AND op2.fecha_finalizacion IS NULL
                        )
                        AND (
                            :filtro IS NULL OR :filtro = '' OR
                            LOWER(CONCAT(p.nombres, ' ', p.apellidos)) LIKE LOWER(CONCAT('%', :filtro, '%')) OR
                            LOWER(p.numero_doc) LIKE LOWER(CONCAT('%', :filtro, '%')) OR
                            LOWER(us.email) LIKE LOWER(CONCAT('%', :filtro, '%')) OR
                            LOWER(p.celular) LIKE LOWER(CONCAT('%', :filtro, '%'))
                        )
                    ORDER BY p.nombres ASC, p.apellidos ASC
                    """,
            countQuery = """
                    SELECT COUNT(*)
                    FROM usuarios us
                    LEFT JOIN personas p ON us.fk_id_persona = p.id
                    WHERE 
                        us.estado = TRUE
                        AND us.cargo = 'JEFE_DE_OPERACION'
                        AND NOT EXISTS (
                            SELECT 1
                            FROM operaciones op2
                            WHERE op2.fk_id_jefe_operacion = us.id
                            AND op2.fecha_finalizacion IS NULL
                        )
                        AND (
                            :filtro IS NULL OR :filtro = '' OR
                            LOWER(CONCAT(p.nombres, ' ', p.apellidos)) LIKE LOWER(CONCAT('%', :filtro, '%')) OR
                            LOWER(p.numero_doc) LIKE LOWER(CONCAT('%', :filtro, '%')) OR
                            LOWER(us.email) LIKE LOWER(CONCAT('%', :filtro, '%')) OR
                            LOWER(p.celular) LIKE LOWER(CONCAT('%', :filtro, '%'))
                        )
                    """,
            nativeQuery = true
    )
    Page<Usuario> findJefesOperacionSinOperacionVigente(
            @Param("filtro") String filtro,
            Pageable pageable
    );


    @Query(
            value = """
                    SELECT us.*
                    FROM usuarios us
                    LEFT JOIN personas p ON us.fk_id_persona = p.id
                    WHERE 
                        us.estado = TRUE
                        AND us.cargo = 'JEFE_DE_OPERACION'
                    
                        AND (
                            -- 1. Usuarios SIN operaciones vigentes
                            NOT EXISTS (
                                SELECT 1
                                FROM operaciones op2
                                WHERE op2.fk_id_jefe_operacion = us.id
                                AND op2.fecha_finalizacion IS NULL
                            )
                    
                            OR
                    
                            -- 2. Usuario es el jefe de la operación que se está editando
                            EXISTS (
                                SELECT 1
                                FROM operaciones op3
                                WHERE op3.id = :idOperacion
                                AND op3.fk_id_jefe_operacion = us.id
                            )
                        )
                    
                        AND (
                            :filtro IS NULL OR :filtro = '' OR
                            LOWER(CONCAT(p.nombres, ' ', p.apellidos)) LIKE LOWER(CONCAT('%', :filtro, '%')) OR
                            LOWER(p.numero_doc) LIKE LOWER(CONCAT('%', :filtro, '%')) OR
                            LOWER(us.email) LIKE LOWER(CONCAT('%', :filtro, '%')) OR
                            LOWER(p.celular) LIKE LOWER(CONCAT('%', :filtro, '%'))
                        )
                    ORDER BY p.nombres ASC, p.apellidos ASC
                    """,

            countQuery = """
                    SELECT COUNT(*)
                    FROM usuarios us
                    LEFT JOIN personas p ON us.fk_id_persona = p.id
                    WHERE 
                        us.estado = TRUE
                        AND us.cargo = 'JEFE_DE_OPERACION'
                    
                        AND (
                            NOT EXISTS (
                                SELECT 1
                                FROM operaciones op2
                                WHERE op2.fk_id_jefe_operacion = us.id
                                AND op2.fecha_finalizacion IS NULL
                            )
                            OR
                            EXISTS (
                                SELECT 1
                                FROM operaciones op3
                                WHERE op3.id = :idOperacion
                                AND op3.fk_id_jefe_operacion = us.id
                            )
                        )
                    
                        AND (
                            :filtro IS NULL OR :filtro = '' OR
                            LOWER(CONCAT(p.nombres, ' ', p.apellidos)) LIKE LOWER(CONCAT('%', :filtro, '%')) OR
                            LOWER(p.numero_doc) LIKE LOWER(CONCAT('%', :filtro, '%')) OR
                            LOWER(us.email) LIKE LOWER(CONCAT('%', :filtro, '%')) OR
                            LOWER(p.celular) LIKE LOWER(CONCAT('%', :filtro, '%'))
                        )
                    """,

            nativeQuery = true
    )
    Page<Usuario> findUsuariosConOperacionesEdit(
            @Param("filtro") String filtro,
            @Param("idOperacion") Long idOperacion,
            Pageable pageable
    );

    @Query(value = """
            SELECT u.*
            FROM usuarios u
            LEFT JOIN operaciones op
                ON op.fk_id_jefe_operacion = u.id
            WHERE
                u.cargo = 'JEFE_DE_OPERACION'
                AND u.estado = true
                AND (
                    op.id IS NULL
                    OR op.fecha_finalizacion IS NOT NULL
                )
                AND NOT EXISTS (
                    SELECT 1
                    FROM operaciones op2
                    WHERE op2.fk_id_jefe_operacion = u.id
                    AND op2.fecha_finalizacion IS NULL
                );
            """,
            nativeQuery = true)
    List<Usuario> findJefesOperacionSinActivas();

    @Query(value = """
            SELECT u.*
            FROM usuarios u
            WHERE
                u.estado = true
                AND u.cargo = :cargo
                AND NOT EXISTS (
                    SELECT 1
                    FROM equipo_usuarios eu2
                    WHERE eu2.fk_id_usuario = u.id
                      AND eu2.fecha_fin IS NULL
                );
            """,
            nativeQuery = true)
    List<Usuario> findSinEquipoActivo(@Param("cargo") String cargo);

    @Query("""
                SELECT 
                    MONTH(u.fechaCreacion) AS mes,
                    COUNT(u.id) AS cantidad
                FROM Usuario u
                WHERE YEAR(u.fechaCreacion) = :anio
                GROUP BY MONTH(u.fechaCreacion)
                ORDER BY mes
            """)
    List<UsuariosPorMesProjection> obtenerUsuariosPorMes(@Param("anio") int anio);

}
