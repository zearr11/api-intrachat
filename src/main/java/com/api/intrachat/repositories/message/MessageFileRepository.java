package com.api.intrachat.repositories.message;

import com.api.intrachat.models.message.MessageFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageFileRepository extends JpaRepository<MessageFile, Long> {
}
