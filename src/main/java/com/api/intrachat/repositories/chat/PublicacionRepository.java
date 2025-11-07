package com.api.intrachat.repositories.chat;

import com.api.intrachat.models.chat.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicacionRepository extends JpaRepository<Publicacion, Long> {
}
