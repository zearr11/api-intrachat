package com.api.intrachat.services.interfaces.campania;

import com.api.intrachat.dto.generics.PaginatedResponse;
import com.api.intrachat.dto.request.CampaniaRequest;
import com.api.intrachat.dto.request.CampaniaRequest2;
import com.api.intrachat.dto.response.CampaniaResponse;
import com.api.intrachat.models.campania.Campania;
import java.util.List;

public interface ICampaniaService {

    Campania obtenerCampaniaPorID(Long id);
    Campania obtenerCampaniaPorNombre(String nombre);

    List<Campania> obtenerCampaniasPorEmpresa(Long idEmpresa);
    // List<Campania> obtenerCampaniasPorSede(Long idSede);

    PaginatedResponse<List<CampaniaResponse>> obtenerCampaniasPaginado(int page, int size,
                                                                       boolean estado, String filtro);

    // String agregarSedeACampania(Long idCampania, Long idSede);
    String crearCampania(CampaniaRequest campaniaRequest);
    String modificarCampania(Long id, CampaniaRequest2 campaniaRequest);

}
