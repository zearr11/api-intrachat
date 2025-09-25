package com.api.intrachat.repositories.mode;

import com.api.intrachat.models.mode.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
}
