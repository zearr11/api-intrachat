package com.api.intrachat.repositories.user;

import com.api.intrachat.models.user.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position, Long> {
}
