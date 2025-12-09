package com.api.intrachat.services.interfaces.campania;

import com.api.intrachat.dto.generics.PaginatedResponse;
import com.api.intrachat.dto.request.EquipoRequest;
import com.api.intrachat.dto.request.EquipoRequest2;
import com.api.intrachat.dto.response.EquipoEspecialResponse;
import com.api.intrachat.dto.response.EquipoResponse;
import com.api.intrachat.dto.response.UsuarioEquipoResponse;
import com.api.intrachat.models.campania.Equipo;
import java.util.List;

public interface IEquipoService {

    Equipo obtenerEquipoPorID(Long id);
    Equipo obtenerEquipoPorGrupo(Long idGrupo);
    EquipoResponse obtenerEquipoResponsePorId(Long id);
    List<Equipo> obtenerEquiposPorOperacion(Long idOperacion);

    PaginatedResponse<List<EquipoEspecialResponse>> obtenerEquiposPaginado(int page, int size,
                                                                           boolean estado, String filtro);
    String crearEquipo(EquipoRequest equipoRequest);
    String modificarEquipo(Long id, EquipoRequest2 equipoRequest);
    String establecerEquipoComoInoperativo(Long idEquipo);

}
