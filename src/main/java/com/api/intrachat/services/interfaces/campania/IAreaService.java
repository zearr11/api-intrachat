package com.api.intrachat.services.interfaces.campania;

import com.api.intrachat.dto.generics.PaginatedResponse;
import com.api.intrachat.dto.response.AreaResponse;
import com.api.intrachat.models.campania.Area;
import com.api.intrachat.dto.request.AreaRequest;
import java.util.List;

public interface IAreaService {

    Area obtenerAreaPorID(Long id);
    List<Area> obtenerAreas();

    PaginatedResponse<List<AreaResponse>> obtenerAreasPaginado(int page, int size);

    String crearArea(AreaRequest areaRequest);
    String modificarArea(Long id, AreaRequest areaRequest);

}
