package com.api.intrachat.repositories.chat;

import com.api.intrachat.models.chat.Integrante;
import com.api.intrachat.models.chat.Sala;
import com.api.intrachat.models.general.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IntegranteRepository extends JpaRepository<Integrante, Long> {
    List<Integrante> findByUsuario(Usuario usuario);
    List<Integrante> findBySala(Sala sala);
}
