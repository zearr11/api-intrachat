package com.api.intrachat.repositories.general;

import com.api.intrachat.models.general.Archivo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArchivoRepository extends JpaRepository<Archivo, Long> {
}
