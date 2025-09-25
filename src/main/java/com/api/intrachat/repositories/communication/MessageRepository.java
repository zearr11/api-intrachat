package com.api.intrachat.repositories.communication;

import com.api.intrachat.models.communication.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
