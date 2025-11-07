package com.api.intrachat.services.interfaces.campania;

import com.api.intrachat.dto.generics.PaginatedResponse;
import com.api.intrachat.dto.response.PaisResponse;
import com.api.intrachat.models.campania.Pais;
import com.api.intrachat.dto.request.PaisRequest;
import java.util.List;

public interface IPaisService {

    Pais obtenerPaisPorID(Long id);
    List<Pais> obtenerPaises();

    PaginatedResponse<List<PaisResponse>> obtenerPaisesPaginado(int page, int size);

    String crearPais(PaisRequest paisRequest);
    String modificarPais(Long id, PaisRequest paisRequest);

}
