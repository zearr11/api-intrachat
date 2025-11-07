package com.api.intrachat.utils.mappers;

import com.api.intrachat.dto.response.AreaResponse;
import com.api.intrachat.models.campania.Area;

public class AreaMapper {

    public static AreaResponse areaResponse(Area area) {
        return new AreaResponse(
                area.getId(), area.getNombre()
        );
    }

}
