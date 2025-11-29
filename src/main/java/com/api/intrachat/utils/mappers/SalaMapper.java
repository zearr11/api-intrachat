package com.api.intrachat.utils.mappers;

import com.api.intrachat.dto.response.IntegranteResponse;
import com.api.intrachat.dto.response.SalaResponse;
import com.api.intrachat.models.chat.Integrante;
import com.api.intrachat.models.chat.Sala;
import java.util.List;

public class SalaMapper {

    public static SalaResponse salaResponse(Sala sala, List<Integrante> integrantes) {

        List<IntegranteResponse> integrantesDeSala = integrantes
                .stream()
                .map(IntegranteMapper::integranteResponse)
                .toList();

        return new SalaResponse(
                sala.getId(),
                sala.getTipoSala(),
                sala.getFechaCreacion(),
                integrantesDeSala
        );
    }

}
