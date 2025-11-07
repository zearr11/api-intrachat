package com.api.intrachat.repositories.general;

import com.api.intrachat.models.general.Persona;
import com.api.intrachat.utils.enums.TipoDoc;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonaRepository extends JpaRepository<Persona, Long> {

    Optional<Persona> findByCelular(String celular);
    Optional<Persona> findByTipoDocAndNumeroDoc(TipoDoc tipoDoc, String numeroDoc);

}
