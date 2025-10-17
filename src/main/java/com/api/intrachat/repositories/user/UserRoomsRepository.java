package com.api.intrachat.repositories.user;

import com.api.intrachat.models.user.UserRooms;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoomsRepository extends JpaRepository<UserRooms, Long> {
}
