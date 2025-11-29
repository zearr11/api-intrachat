package com.api.intrachat.services.interfaces.chat;

import com.api.intrachat.dto.generics.PaginatedResponse;
import com.api.intrachat.dto.response.MensajeResponse;
import com.api.intrachat.models.chat.Mensaje;
import com.api.intrachat.models.chat.Texto;
import com.api.intrachat.models.general.Archivo;
import java.util.List;

public interface IMensajeService {

    // Entidad Mensaje
    Mensaje obtenerMensajePorId(Long idMensaje);
    Mensaje obtenerUltimoMensajeDeSala (Long idSala);
    PaginatedResponse<List<MensajeResponse>> obtenerMensajesPorSala(int page, int size,
                                                                    String filtro, Long idSala);

    // Entidad Texto
    Texto obtenerTextoDeMensaje(Long idMensaje);
    Archivo obtenerArchivoDeMensaje(Long idMensaje);

}
