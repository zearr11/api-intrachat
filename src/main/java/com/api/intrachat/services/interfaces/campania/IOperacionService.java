package com.api.intrachat.services.interfaces.campania;

import com.api.intrachat.dto.generics.PaginatedResponse;
import com.api.intrachat.dto.request.OperacionRequest;
import com.api.intrachat.dto.response.OperacionResponse;
import com.api.intrachat.models.campania.Operacion;
import java.util.List;

public interface IOperacionService {

    Operacion obtenerOperacionPorID(Long id);
    PaginatedResponse<List<OperacionResponse>> obtenerOperacionesPaginado(int page, int size,
                                                                          boolean estado, Long idCampania,
                                                                          Long idJefeOperacion);
    String crearOperacion(OperacionRequest operacionRequest);
    String finalizarOperacion(Long idOperacion);

}
