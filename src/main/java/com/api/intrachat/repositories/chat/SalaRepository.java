package com.api.intrachat.repositories.chat;

import com.api.intrachat.models.chat.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SalaRepository extends JpaRepository<Sala, Long> {

    @Query("""
    SELECT s
    FROM Sala s
    WHERE s.tipoSala = 'PRIVADO'
      AND s.id IN (
            SELECT i1.sala.id
            FROM Integrante i1
            JOIN Integrante i2 ON i1.sala.id = i2.sala.id
            WHERE i1.usuario.id = :idUsuario1
              AND i2.usuario.id = :idUsuario2
      )
    """)
    Sala obtenerSalaPrivadaPorIntegrantes(
            @Param("idUsuario1") Long idUsuario1,
            @Param("idUsuario2") Long idUsuario2
    );


}
