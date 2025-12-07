package com.api.intrachat.utils.mappers;

import com.api.intrachat.models.general.Usuario;
import com.api.intrachat.dto.response.UsuarioResponse;

public class UsuarioMapper {

    public static UsuarioResponse usuarioResponse(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getImagenPerfil().getUrl(),
                usuario.getPersona().getNombres(),
                usuario.getPersona().getApellidos(),
                usuario.getPersona().getTipoDoc(),
                usuario.getPersona().getNumeroDoc(),
                usuario.getPersona().getGenero(),
                usuario.getPersona().getCelular(),
                usuario.getEmail(),
                usuario.getCargo(),
                usuario.getRol(),
                usuario.getFechaCreacion(),
                usuario.getUltimaModificacion(),
                usuario.getEstado(),
                usuario.getPersona().getInformacion()
        );
    }

    /*
    sede.getPais().getNombre(),
    sede.getNombre(),
    campania.getNombre(),
    equipo.getNumeroEquipo()
    */

}
