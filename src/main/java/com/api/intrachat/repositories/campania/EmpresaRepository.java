package com.api.intrachat.repositories.campania;

import com.api.intrachat.models.campania.Empresa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    Optional<Empresa> findByNombre(String nombre);

    @Query("""
       SELECT e FROM Empresa e
       WHERE (:estado IS NULL OR e.estado = :estado)
       AND (:filtro IS NULL OR :filtro = '' OR
            LOWER(e.nombre) LIKE LOWER(CONCAT('%', :filtro, '%')))
       """)
    Page<Empresa> buscarPorFiltro(
            @Param("filtro") String filtro,
            @Param("estado") Boolean estado,
            Pageable pageable);

}
