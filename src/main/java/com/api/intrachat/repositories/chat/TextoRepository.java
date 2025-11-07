package com.api.intrachat.repositories.chat;

import com.api.intrachat.models.chat.Texto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TextoRepository extends JpaRepository<Texto, Long> {
}
