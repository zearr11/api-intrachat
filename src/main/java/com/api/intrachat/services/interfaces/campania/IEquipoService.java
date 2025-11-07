package com.api.intrachat.services.interfaces.campania;

import com.api.intrachat.dto.generics.PaginatedResponse;
import com.api.intrachat.dto.request.EquipoRequest;
import com.api.intrachat.dto.response.EquipoResponse;
import com.api.intrachat.models.campania.Equipo;
import java.util.List;

public interface IEquipoService {

    Equipo obtenerEquipoPorID(Long id);
    Equipo obtenerEquipoPorGrupo(Long idGrupo);

    PaginatedResponse<List<EquipoResponse>> obtenerEquiposPaginado(int page, int size, int numeroEquipo,
                                                                   Long idGrupo, Long idCampania, Long idSede,
                                                                   Long idUsuario);

    String crearEquipo(EquipoRequest equipoRequest);
    String finalizarRealacionDeUsuarioConEquipo(Long idEquipo, Long idUsuario);

}
