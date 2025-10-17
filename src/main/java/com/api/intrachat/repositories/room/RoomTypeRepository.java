package com.api.intrachat.repositories.room;

import com.api.intrachat.models.room.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomTypeRepository extends JpaRepository<RoomType, Long> {
}
