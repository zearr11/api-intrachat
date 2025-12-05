package com.api.intrachat.utils.mappers;

import com.api.intrachat.dto.response.SedeResponse;
import com.api.intrachat.models.campania.Sede;

public class SedeMapper {

    public static SedeResponse sedeResponse(Sede sede) {
        return new SedeResponse(
                sede.getId(),
                sede.getNombre(),
                sede.getDireccion(),
                sede.getNumeroPostal(),
                sede.getEstado()
        );
    }

}
