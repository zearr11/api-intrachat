package com.api.intrachat.repositories.room;

import com.api.intrachat.models.room.RoomDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomDetailsRepository extends JpaRepository<RoomDetails, Long> {
}
