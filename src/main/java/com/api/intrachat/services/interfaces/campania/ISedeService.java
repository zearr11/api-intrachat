package com.api.intrachat.services.interfaces.campania;

import com.api.intrachat.dto.generics.PaginatedResponse;
import com.api.intrachat.dto.response.SedeResponse;

import java.util.List;

public interface ISedeService {

    PaginatedResponse<List<SedeResponse>> obtenerSedesPaginado(int page, int size,
                                                               boolean estado, String filtro);
}
