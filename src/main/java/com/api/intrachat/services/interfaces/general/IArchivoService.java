package com.api.intrachat.services.interfaces.general;

import com.api.intrachat.models.general.Archivo;
import org.springframework.web.multipart.MultipartFile;

public interface IArchivoService {

    Archivo obtenerArchivoPorID(Long id);

    Archivo crearArchivo(MultipartFile archivo);
    void modificarArchivo(Archivo archivoActual, MultipartFile archivo);

    void verificarArchivoValido(MultipartFile archivo);
    void esArchivoImagen(MultipartFile archivo);
    void esOtroTipoDeArchivo(MultipartFile archivo);

}
