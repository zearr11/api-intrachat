package com.api.intrachat.services.interfaces.campania;

import com.api.intrachat.dto.generics.PaginatedResponse;
import com.api.intrachat.dto.request.EquipoRequest;
import com.api.intrachat.dto.response.EquipoEspecialResponse;
import com.api.intrachat.dto.response.UsuarioEquipoResponse;
import com.api.intrachat.models.campania.Equipo;
import java.util.List;

public interface IEquipoService {

    Equipo obtenerEquipoPorID(Long id);
    Equipo obtenerEquipoPorGrupo(Long idGrupo);

    PaginatedResponse<List<UsuarioEquipoResponse>> obtenerUsuariosEquipoPaginado(int page, int size,
                                                                                 boolean estado, Long idCampania,
                                                                                 Long idOperacion, Long idEquipo);
    PaginatedResponse<List<EquipoEspecialResponse>> obtenerEquiposPaginado(int page, int size,
                                                                           boolean estado, String filtro);
    String crearEquipo(EquipoRequest equipoRequest);
    String deshabilitarEquipo(Long idEquipo);
    String agregarUsuarioAEquipo(Long idEquipo, Long idUsuario);
    String cambiarPermisoDeUsuarioEnEquipo(Long idEquipo, Long idUsuario);
    String finalizarRelacionDeUsuarioConEquipo(Long idEquipo, Long idUsuario);

}
