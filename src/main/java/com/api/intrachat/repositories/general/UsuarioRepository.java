package com.api.intrachat.repositories.general;

import com.api.intrachat.models.general.Persona;
import com.api.intrachat.models.general.Usuario;
import com.api.intrachat.utils.enums.Rol;
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
        AND (:roles IS NULL OR u.rol IN :roles)
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
            @Param("roles") List<Rol> roles,
            @Param("filtro") String filtro,
            Pageable pageable
    );

    Optional<Usuario> findByEmail(String email);
    Usuario findByPersona(Persona persona);

}
