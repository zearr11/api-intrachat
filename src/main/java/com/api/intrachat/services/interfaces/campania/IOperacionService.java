package com.api.intrachat.services.interfaces.campania;

import com.api.intrachat.dto.generics.PaginatedResponse;
import com.api.intrachat.dto.request.OperacionRequest;
import com.api.intrachat.dto.response.OperacionEspecialResponse;
import com.api.intrachat.models.campania.Operacion;
import java.util.List;

public interface IOperacionService {

    Operacion obtenerOperacionPorID(Long id);
    List<Operacion> obtenerOperacionesPorSede(Long idSede);
    List<Operacion> obtenerOperacionesPorCampania(Long idCampania);

    PaginatedResponse<List<OperacionEspecialResponse>> obtenerOperacionesPaginado(int page, int size,
                                                                                  boolean estado, Long idCampania,
                                                                                  String filtro);
    String crearOperacion(OperacionRequest operacionRequest);
    String finalizarOperacion(Long idOperacion);

}
