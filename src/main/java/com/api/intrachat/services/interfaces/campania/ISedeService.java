package com.api.intrachat.services.interfaces.campania;

import com.api.intrachat.dto.generics.PaginatedResponse;
import com.api.intrachat.dto.request.SedeRequest;
import com.api.intrachat.dto.request.SedeRequest2;
import com.api.intrachat.dto.response.SedeResponse;
import com.api.intrachat.models.campania.Sede;
import java.util.List;

public interface ISedeService {

    Sede obtenerSedePorID(Long id);
    List<Sede> obtenerSedes();

    PaginatedResponse<List<SedeResponse>> obtenerSedesPaginado(int page, int size,
                                                               boolean estado, String filtro);

    String crearSede(SedeRequest sedeRequest);
    String modificarSede(Long id, SedeRequest2 sedeRequest);
}
