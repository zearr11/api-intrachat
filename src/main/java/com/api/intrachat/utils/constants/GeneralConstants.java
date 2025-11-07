package com.api.intrachat.utils.constants;

public class GeneralConstants {

    public final static Boolean ESTADO_DEFAULT = true;

    public static final String MENSAJE_GENERICO_NO_EXISTE = "No se encontraron resultados.";

    public static String mensajeEntidadNoExiste(String entidad, String codigo) {
        return entidad + " con código " + codigo + " no existe.";
    }

    public static String mensajeEntidadCreada(String entidad) {
        return entidad + " creado.";
    }

    public static String mensajeEntidadAgregada(String entidad) {
        return entidad + " agregada.";
    }

    public static String mensajeEntidadActualizada(String entidad) {
        return entidad + " actualizado.";
    }

    public static String mensajeEntidadYaRegistrada(String entidad) {
        return entidad + " ya se encuentra registrado en el sistema.";
    }

}
