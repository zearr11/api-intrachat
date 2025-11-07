package com.api.intrachat.utils.constants;

public class UsuarioConstants {

    public final static String INFO_DEFAULT = "Disponible";
    public final static Long ID_ARCHIVO_DEFAULT = 1L;
    public final static String ASUNTO_DEFAULT_BIENVENIDA_EMAIL = "Bienvenido a Covisian";

    public static String mensajeEmailNoExiste(String email) {
        return "Usuario con email " + email + " no existe.";
    }

    public static String mensajeCelularNoExiste(String celular) {
        return "Usuario con celular " + celular + " no existe.";
    }

    public final static String ERROR_TIPO_DOC_NUM_DOC_REGISTRADO = "Numero de documento ya registrado.";
    public final static String ERROR_CELULAR_REGISTRADO = "Numero de celular ya registrado.";
    public final static String ERROR_EMAIL_REGISTRADO = "Email ya registrado.";

}
