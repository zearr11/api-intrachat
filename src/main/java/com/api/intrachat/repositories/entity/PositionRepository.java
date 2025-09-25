package com.api.intrachat.repositories.entity;

import com.api.intrachat.models.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position, Long> {
}
