package com.api.intrachat.repositories.campania;

import com.api.intrachat.models.campania.Equipo;
import com.api.intrachat.models.campania.EquipoUsuarios;
import com.api.intrachat.models.general.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EquipoUsuariosRepository extends JpaRepository<EquipoUsuarios, Long> {
    List<EquipoUsuarios> findByUsuario(Usuario usuario);

    List<EquipoUsuarios> findByEquipo(Equipo equipo);
}
