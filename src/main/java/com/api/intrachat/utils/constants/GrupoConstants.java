package com.api.intrachat.utils.constants;

import com.api.intrachat.models.campania.Campania;

public class GrupoConstants {
    public final static Long ID_ARCHIVO_DEFAULT = 2L;

    public final static String DESCRIPCION_DEFAULT = """
        Espacio para la comunicación entre los colaboradores de la campaña. Aquí puedes consultar procesos de tu gestión, solicitar apoyo del equipo y mantener una interacción general.
        """.stripIndent();

    public static String generarNombreGrupo(Campania campania, String apellidoSupervisor) {
        final String nombreEmpresa = campania.getEmpresa().getNombreComercial();
        final String areaAtencion = campania.getAreaAtencion().toTitleCase();
        final String medioComunicacion = campania.getMedioComunicacion().toTitleCase();
        final String grupoGenerico = "Equipo " + apellidoSupervisor + " - ";

        return grupoGenerico + nombreEmpresa + " " + areaAtencion + " " + medioComunicacion;
    }
}
