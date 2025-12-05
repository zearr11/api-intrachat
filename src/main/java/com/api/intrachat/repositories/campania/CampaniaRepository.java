package com.api.intrachat.repositories.campania;

import com.api.intrachat.models.campania.Campania;
import com.api.intrachat.models.campania.Empresa;
import com.api.intrachat.repositories.campania.projections.CampaniaProjection;
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

    /*
    @Query("SELECT c FROM Campania c JOIN c.campaniaSedes s WHERE s.id = :idSede")
    List<Campania> findBySedeId(@Param("idSede") Long idSede);
    */

    @Query(
            value = """
            SELECT 
                c.id,
                e.nombre_comercial AS nombreComercialEmpresa,
                c.area_atencion AS areaAtencion,
                c.medio_comunicacion AS medioComunicacion,

                /* Totales de operaciones */
                (SELECT COUNT(*) 
                 FROM operaciones o 
                 WHERE o.fk_id_campania = c.id AND o.fecha_finalizacion IS NULL) AS totalOperacionesActivas,

                (SELECT COUNT(*) 
                 FROM operaciones o 
                 WHERE o.fk_id_campania = c.id AND o.fecha_finalizacion IS NOT NULL) AS totalOperacionesInactivas,

                /* Totales de equipos */
                (SELECT COUNT(*) 
                 FROM equipos eq 
                 JOIN operaciones o ON eq.fk_id_operacion = o.id
                 WHERE o.fk_id_campania = c.id AND eq.estado = 1) AS totalEquiposActivos,

                (SELECT COUNT(*) 
                 FROM equipos eq 
                 JOIN operaciones o ON eq.fk_id_operacion = o.id
                 WHERE o.fk_id_campania = c.id AND eq.estado = 0) AS totalEquiposInactivos,

                /* Totales de usuarios */
                (SELECT COUNT(*) 
                 FROM equipo_usuarios eu
                 JOIN equipos eq ON eu.fk_id_equipo = eq.id
                 JOIN operaciones o ON eq.fk_id_operacion = o.id
                 WHERE o.fk_id_campania = c.id AND eu.estado = 1) AS totalUsuariosActivos,

                (SELECT COUNT(*) 
                 FROM equipo_usuarios eu
                 JOIN equipos eq ON eu.fk_id_equipo = eq.id
                 JOIN operaciones o ON eq.fk_id_operacion = o.id
                 WHERE o.fk_id_campania = c.id AND eu.estado = 0) AS totalUsuariosInactivos,

                c.estado,
                c.fecha_creacion AS fechaCreacion,
                c.ultima_modificacion AS ultimaModificacion

            FROM campanias c
            JOIN empresas e ON c.fk_id_empresa = e.id

            WHERE 
                (
                    :filtro IS NULL 
                    OR e.nombre_comercial LIKE CONCAT('%', :filtro, '%')
                    OR c.area_atencion LIKE CONCAT('%', :filtro, '%')
                    OR c.medio_comunicacion LIKE CONCAT('%', :filtro, '%')
                )
                AND (:estado IS NULL OR c.estado = :estado)
            """,

            countQuery = """
            SELECT COUNT(*)
            FROM campanias c
            JOIN empresas e ON c.fk_id_empresa = e.id
            WHERE 
                (
                    :filtro IS NULL 
                    OR e.nombre_comercial LIKE CONCAT('%', :filtro, '%')
                    OR c.area_atencion LIKE CONCAT('%', :filtro, '%')
                    OR c.medio_comunicacion LIKE CONCAT('%', :filtro, '%')
                )
                AND (:estado IS NULL OR c.estado = :estado)
            """,

            nativeQuery = true
    )
    Page<CampaniaProjection> buscarCampaniasPaginado(
            @Param("filtro") String filtro,
            @Param("estado") Boolean estado,
            Pageable pageable
    );


}
