package com.api.intrachat.repositories.mode;

import com.api.intrachat.models.mode.GroupUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupUserRepository extends JpaRepository<GroupUser, Long> {
}
