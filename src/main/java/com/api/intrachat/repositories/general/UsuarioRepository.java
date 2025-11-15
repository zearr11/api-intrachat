package com.api.intrachat.repositories.general;

import com.api.intrachat.models.general.Persona;
import com.api.intrachat.models.general.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

}
