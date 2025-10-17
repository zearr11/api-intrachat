package com.api.intrachat.repositories.message;

import com.api.intrachat.models.message.AnswerMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerMessageRepository extends JpaRepository<AnswerMessage, Long> {
}
