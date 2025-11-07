package com.api.intrachat.repositories.chat;

import com.api.intrachat.models.chat.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MensajeRepository extends JpaRepository<Mensaje, Long> {
}
