package com.api.intrachat.services.impl.general;

import com.api.intrachat.models.general.Archivo;
import com.api.intrachat.repositories.general.ArchivoRepository;
import com.api.intrachat.services.interfaces.general.IArchivoService;
import com.api.intrachat.services.interfaces.other.ICloudinaryService;
import com.api.intrachat.utils.constants.ResponseConstants;
import com.api.intrachat.utils.constants.UsuarioConstants;
import com.api.intrachat.utils.exceptions.errors.ErrorException400;
import com.api.intrachat.utils.exceptions.errors.ErrorException404;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class ArchivoService implements IArchivoService {

    private final ArchivoRepository archivoRepository;
    private final ICloudinaryService cloudinaryService;

    public ArchivoService(ArchivoRepository archivoRepository,
                          ICloudinaryService cloudinaryService) {
        this.archivoRepository = archivoRepository;
        this.cloudinaryService = cloudinaryService;
    }

    @Override
    public Archivo obtenerArchivoPorID(Long id) {
        return archivoRepository.findById(id).orElseThrow(
                () -> new ErrorException404(
                        ResponseConstants.mensajeEntidadNoExiste("Archivo", id.toString())
                )
        );
    }

    @Override
    public Archivo crearArchivo(MultipartFile archivo) {

        // Realiza la validación si el archivo es valido, sino lanza exception
        verificarArchivoValido(archivo);

        // Subida de archivo a Cloudinary
        final String secureUrl = cloudinaryService.subirArchivo(archivo);

        Archivo nuevoArchivo = Archivo.builder()
                .nombre(archivo.getOriginalFilename())
                .url(secureUrl)
                .tipo(archivo.getContentType())
                .tamanio((double) archivo.getSize())
                .build();

        // Subida a bd y retornar resultado
        return archivoRepository.save(nuevoArchivo);
    }

    @Override
    public void modificarArchivo(Archivo archivoActual, MultipartFile archivo) {

        // Realiza la validación si el archivo es válido, sino lanza exception
        verificarArchivoValido(archivo);

        // Validación: Es true si el archivo nuevo es igual al actual
        if (cloudinaryService.nuevoArchivoIgualAlActual(archivo, archivoActual.getUrl()))
            return;

        // Eliminación del archivo en cloudinary
        cloudinaryService.eliminarArchivo(archivoActual.getUrl());

        // Subida de nuevo archivo a cloudinary y obtención del nuevo url
        final String secureUrl = cloudinaryService.subirArchivo(archivo);

        // Reasignación de nuevos atributos
        archivoActual.setUrl(secureUrl);
        archivoActual.setTipo(archivo.getContentType());
        archivoActual.setTamanio((double) archivo.getSize());
        archivoActual.setNombre(archivo.getOriginalFilename());

        // Actualización de entidad
        archivoRepository.save(archivoActual);
    }

    // privado
    @Override
    public void verificarArchivoValido(MultipartFile archivo) {

        if (archivo == null || archivo.isEmpty()) {
            throw new ErrorException400("El archivo es obligatorio.");
        }

        if (archivo.getSize() > 10 * 1024 * 1024) {
            throw new ErrorException400("El archivo excede el tamaño máximo permitido de 10 MB.");
        }

    }

    // publico
    @Override
    public void esArchivoImagen(MultipartFile archivo) {
        String tipoArchivo = archivo.getContentType();
        String nombreArchivo = archivo.getOriginalFilename();

        if (archivo.isEmpty()) {
            throw new ErrorException400("Debe subir un archivo.");
        }

        if (tipoArchivo == null || !tipoArchivo.startsWith("image/")) {
            throw new ErrorException400("El archivo debe ser una imagen válida.");
        }
        if (nombreArchivo == null ||
                !(nombreArchivo.endsWith(".jpg") ||
                        nombreArchivo.endsWith(".jpeg") ||
                        nombreArchivo.endsWith(".png") ||
                        nombreArchivo.endsWith(".gif") ||
                        nombreArchivo.endsWith(".bmp") ||
                        nombreArchivo.endsWith(".webp"))) {
            throw new ErrorException400(
                    "El archivo debe tener una extensión de imagen válida (jpg, png, gif, bmp, webp)."
            );
        }

        try (InputStream input = archivo.getInputStream()) {
            if (ImageIO.read(input) == null) {
                throw new ErrorException400(
                        "El archivo no contiene una imagen válida."
                );
            }
        } catch (IOException e) {
            throw new ErrorException400(
                    "No se pudo procesar el archivo de imagen."
            );
        }

    }

    // publico
    @Override
    public void esOtroTipoDeArchivo(MultipartFile archivo) {

        String tipoArchivo = archivo.getContentType();
        String nombreArchivo = archivo.getOriginalFilename();

        if (archivo.isEmpty()) {
            throw new ErrorException400("Debe subir un archivo.");
        }

        if (tipoArchivo == null || nombreArchivo == null) {
            throw new ErrorException400("El archivo no es válido.");
        }

        List<String> tiposPermitidos = List.of(
                "application/pdf",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document", // .docx
                "application/msword", // .doc
                "text/plain",         // .txt
                "application/vnd.ms-excel", // .xls
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" // .xlsx
        );

        if (!tiposPermitidos.contains(tipoArchivo)) {
            throw new ErrorException400(
                    "El tipo de archivo no está permitido. Solo se admiten PDF, Word, Excel o TXT."
            );
        }

        if (!(nombreArchivo.toLowerCase().endsWith(".pdf")
                || nombreArchivo.toLowerCase().endsWith(".doc")
                || nombreArchivo.toLowerCase().endsWith(".docx")
                || nombreArchivo.toLowerCase().endsWith(".xls")
                || nombreArchivo.toLowerCase().endsWith(".xlsx")
                || nombreArchivo.toLowerCase().endsWith(".txt"))) {
            throw new ErrorException400(
                    "El archivo debe tener una extensión válida (.pdf, .doc, .docx, .xls, .xlsx, .txt)."
            );
        }

    }

}
