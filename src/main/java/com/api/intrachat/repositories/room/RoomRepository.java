package com.api.intrachat.repositories.room;

import com.api.intrachat.models.room.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
