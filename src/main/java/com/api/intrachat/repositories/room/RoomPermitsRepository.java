package com.api.intrachat.repositories.room;

import com.api.intrachat.models.room.RoomPermits;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomPermitsRepository extends JpaRepository<RoomPermits, Long> {
}
