package com.api.intrachat.repositories.general;

import com.api.intrachat.models.general.Publication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicationRepository extends JpaRepository<Publication, Long> {
}
