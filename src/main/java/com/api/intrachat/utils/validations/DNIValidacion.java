package com.api.intrachat.utils.validations;

import com.api.intrachat.utils.enums.TipoDoc;
import com.api.intrachat.utils.exceptions.errors.ErrorException400;

public class DNIValidacion {

    public static void documentoIdentidadValido(String numeroDoc, TipoDoc tipoDoc) {

        boolean esDocValido = false;

        if (tipoDoc.equals(TipoDoc.DNI) && numeroDoc.length() == 8)
            esDocValido = true;
        else if (tipoDoc.equals(TipoDoc.CE) &&
                (numeroDoc.length() >= 8 && numeroDoc.length() <= 13))
            esDocValido = true;

        if (!esDocValido)
            throw new ErrorException400("El numero de documento ingresado, no es válido.");
    }

}
