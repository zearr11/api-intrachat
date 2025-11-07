package com.api.intrachat.repositories.campania;

import com.api.intrachat.models.campania.Pais;
import com.api.intrachat.models.campania.Sede;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface SedeRepository extends JpaRepository<Sede, Long> {

    Optional<List<Sede>> findByPais(Pais pais);
    Optional<Sede> findByNombre(String nombre);

}
