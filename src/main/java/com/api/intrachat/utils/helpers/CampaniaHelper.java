package com.api.intrachat.utils.helpers;

import com.api.intrachat.utils.enums.AreaAtencion;
import com.api.intrachat.utils.enums.MedioComunicacion;

public class CampaniaHelper {

    public static String generarNombreCampania(String nombreComercialEmpresa, AreaAtencion areaAtencion,
                                               MedioComunicacion medioComunicacion) {
        return nombreComercialEmpresa + " " + areaAtencion.toTitleCase() + " ("
                + medioComunicacion.toTitleCase() + ")";
    }

}
