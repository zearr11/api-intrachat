package com.api.intrachat.utils.mappers;

import com.api.intrachat.dto.response.MensajeResponse;
import com.api.intrachat.dto.response.customized.ArchivoResponse;
import com.api.intrachat.dto.response.customized.contact.DatosUsuarioResponse;
import com.api.intrachat.models.chat.Mensaje;
import com.api.intrachat.models.chat.Texto;
import com.api.intrachat.models.general.Archivo;
import com.api.intrachat.utils.helpers.UsuarioHelper;

public class MensajeMapper {

    public static MensajeResponse mensajeResponse(Mensaje mensaje, Archivo archivo,
                                                  Texto texto) {
        String contenido = (texto != null) ? texto.getContenido() : null;
        DatosUsuarioResponse remitente = (mensaje.getUsuario() != null)
                ? new DatosUsuarioResponse(
                        mensaje.getUsuario().getId(),
                        mensaje.getUsuario().getImagenPerfil().getUrl(),
                        UsuarioHelper.obtenerNombreCorto(
                                mensaje.getUsuario().getPersona().getNombres(),
                                mensaje.getUsuario().getPersona().getApellidos()
                        ),
                        mensaje.getUsuario().getPersona().getInformacion())
                : null;
        ArchivoResponse archivoMensaje = (archivo != null)
                ? new ArchivoResponse(
                        archivo.getId(),
                        archivo.getNombre(),
                        archivo.getTamanio(),
                        archivo.getTipo(),
                        archivo.getUrl())
                : null;

        return new MensajeResponse(
                mensaje.getId(),
                remitente,
                mensaje.getTipo(),
                mensaje.getFechaCreacion(),
                archivoMensaje,
                contenido
        );
    }

}
