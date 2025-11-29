package com.api.intrachat.repositories.chat;

import com.api.intrachat.models.chat.Fichero;
import com.api.intrachat.models.chat.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FicheroRepository extends JpaRepository<Fichero, Long> {
    Fichero findByMensaje(Mensaje mensaje);
}
