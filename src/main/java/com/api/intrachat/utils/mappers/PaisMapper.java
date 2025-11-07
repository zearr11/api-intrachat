package com.api.intrachat.utils.mappers;

import com.api.intrachat.dto.response.PaisResponse;
import com.api.intrachat.models.campania.Pais;

public class PaisMapper {

    public static PaisResponse paisResponse(Pais pais) {
        return new PaisResponse(
                pais.getId(), pais.getNombre()
        );
    }

}
