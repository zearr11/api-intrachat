package com.api.intrachat.repositories.general;

import com.api.intrachat.models.general.Persona;
import com.api.intrachat.models.general.Usuario;
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
        AND (
            :filtro IS NULL OR :filtro = '' OR
            LOWER(CONCAT(p.nombres, ' ', p.apellidos)) LIKE LOWER(CONCAT('%', :filtro, '%')) OR
            LOWER(p.numeroDoc) LIKE LOWER(CONCAT('%', :filtro, '%')) OR
            LOWER(u.email) LIKE LOWER(CONCAT('%', :filtro, '%')) OR
            LOWER(p.celular) LIKE LOWER(CONCAT('%', :filtro, '%'))
        )
    """)
    Page<Usuario> buscarUsuariosConPaginacion(
            @Param("estado") Boolean estado,
            @Param("idUsuarioActual") Long idUsuarioActual,
            @Param("filtro") String filtro,
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
          AND e.estado = true
          AND o.estado = true
          AND c.estado = true
          AND LOWER(CONCAT(u.persona.nombres, ' ', u.persona.apellidos))
              LIKE LOWER(CONCAT('%', :filtro, '%'))
    """)
    List<Usuario> buscarContactosPorCampania(
            @Param("filtro") String filtro,
            @Param("idCampania") Long idCampania,
            @Param("idUsuarioExcluir") Long idUsuarioExcluir
    );

}
