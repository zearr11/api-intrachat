package com.api.intrachat.repositories.message;

import com.api.intrachat.models.message.MessageType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageTypeRepository extends JpaRepository<MessageType, Long> {
}
