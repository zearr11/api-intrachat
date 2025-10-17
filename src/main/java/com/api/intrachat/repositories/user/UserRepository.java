package com.api.intrachat.repositories.user;

import com.api.intrachat.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByNumberPhone(String numberPhone);

}
