package com.api.intrachat.repositories.chat;

import com.api.intrachat.models.chat.Grupo;
import com.api.intrachat.models.chat.Sala;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GrupoRepository extends JpaRepository<Grupo, Long> {
    Optional<Grupo> findBySala(Sala Sala);
}
