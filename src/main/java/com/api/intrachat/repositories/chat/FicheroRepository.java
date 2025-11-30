package com.api.intrachat.repositories.chat;

import com.api.intrachat.models.chat.Fichero;
import com.api.intrachat.models.chat.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FicheroRepository extends JpaRepository<Fichero, Long> {
    Optional<Fichero> findByMensaje(Mensaje mensaje);
}
