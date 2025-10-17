package com.api.intrachat.repositories.message;

import com.api.intrachat.models.message.RespondMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RespondMessageRepository extends JpaRepository<RespondMessage, Long> {
}
