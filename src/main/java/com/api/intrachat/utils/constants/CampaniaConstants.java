package com.api.intrachat.utils.constants;

public class CampaniaConstants {

    public final static String ERROR_NOMBRE_REGISTRADO = "La campaña que intenta registrar ya se encuentra vigente.";

    public static String mensajeNombreNoExiste(String nombre) {
        return "La Campaña '" + nombre + "' no existe.";
    }

}
