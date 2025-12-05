package com.api.intrachat.repositories.campania;

import com.api.intrachat.models.campania.Sede;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SedeRepository extends JpaRepository<Sede, Long> {

    @Query("""
                SELECT s FROM Sede s
                WHERE s.estado = :estado
                  AND (
                        :filtro IS NULL
                        OR :filtro = ''
                        OR LOWER(s.nombre) LIKE LOWER(CONCAT('%', :filtro, '%'))
                        OR LOWER(s.direccion) LIKE LOWER(CONCAT('%', :filtro, '%'))
                        OR CAST(s.numeroPostal AS string) LIKE CONCAT('%', :filtro, '%')
                      )
            """)
    Page<Sede> buscarPorFiltro(
            @Param("filtro") String filtro,
            @Param("estado") boolean estado,
            Pageable pageable
    );

}
