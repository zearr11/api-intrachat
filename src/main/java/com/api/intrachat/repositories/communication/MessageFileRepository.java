package com.api.intrachat.repositories.communication;

import com.api.intrachat.models.communication.MessageFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageFileRepository extends JpaRepository<MessageFile, Long> {
}
