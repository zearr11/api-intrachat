package com.api.intrachat.services.interfaces.other;

import org.springframework.web.multipart.MultipartFile;

public interface ICloudinaryService {

    String subirArchivo(MultipartFile archivo, boolean esImagen);
    boolean nuevoArchivoIgualAlActual(MultipartFile nuevoArchivo, String urlArchivoActual);
    void eliminarArchivo(String urlArchivo);
    String extraerPublicIdDelUrl(String urlArchivo);

}
