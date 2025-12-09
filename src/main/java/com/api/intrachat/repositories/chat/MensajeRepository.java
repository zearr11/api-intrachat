package com.api.intrachat.repositories.chat;

import com.api.intrachat.models.chat.Mensaje;
import com.api.intrachat.repositories.chat.projection.MensajesPorMesProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface MensajeRepository extends JpaRepository<Mensaje, Long> {
    Optional<Mensaje> findFirstBySalaIdOrderByFechaCreacionDesc(Long idSala);

    @Query("""
                SELECT m
                FROM Mensaje m
                LEFT JOIN Texto t ON t.mensaje.id = m.id
                WHERE m.sala.id = :idSala
                  AND (
                        :filtro IS NULL
                        OR :filtro = ''
                        OR LOWER(t.contenido) LIKE LOWER(CONCAT('%', :filtro, '%'))
                      )
                ORDER BY m.fechaCreacion DESC
            """)
    Page<Mensaje> obtenerMensajesDeSala(@Param("idSala") Long idSala,
                                        @Param("filtro") String filtro,
                                        Pageable pageable);

    @Query("""
                SELECT
                    MONTH(m.fechaCreacion) AS mes,
                    COUNT(m.id) AS cantidad
                FROM Mensaje m
                WHERE YEAR(m.fechaCreacion) = :anio
                GROUP BY MONTH(m.fechaCreacion)
                ORDER BY mes
            """)
    List<MensajesPorMesProjection> obtenerMensajesPorMes(@Param("anio") int anio);

}
