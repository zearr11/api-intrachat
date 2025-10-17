package com.api.intrachat.repositories.general;

import com.api.intrachat.models.general.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}
