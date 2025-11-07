package com.api.intrachat.services.impl.other;

import com.api.intrachat.utils.exceptions.errors.ErrorException400;
import com.api.intrachat.services.interfaces.other.ICloudinaryService;
import com.api.intrachat.utils.constants.CloudinaryConstants;
import com.api.intrachat.utils.exceptions.errors.ErrorException409;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService implements ICloudinaryService {

    private final Cloudinary cloudinary;
    private final RestTemplate restTemplate;

    public CloudinaryService(Cloudinary cloudinary, RestTemplate restTemplate) {
        this.cloudinary = cloudinary;
        this.restTemplate = restTemplate;
    }

    @Override
    public String subirArchivo(MultipartFile archivo) {
        try {
            Map<?, ?> uploadResult = cloudinary.uploader()
                    .upload(archivo.getBytes(), Map.of(
                            CloudinaryConstants.KEY_LOCATION_UPLOAD_FILE,
                            CloudinaryConstants.VALUE_LOCATION_UPLOAD_FILE
                    ));

            return uploadResult.get(
                    CloudinaryConstants.SECURE_URL_CLOUDINARY
            ).toString();

        } catch (IOException e) {
            throw new ErrorException400(CloudinaryConstants.MESSAGE_ERROR_UPLOAD);
        }
    }

    @Override
    public boolean nuevoArchivoIgualAlActual(MultipartFile nuevoArchivo, String urlArchivoActual) {
        try {
            byte[] imagenActualBytes = restTemplate.getForObject(urlArchivoActual, byte[].class);
            byte[] nuevaImagenBytes = nuevoArchivo.getBytes();

            String hashActual = DigestUtils.sha256Hex(imagenActualBytes);
            String hashNueva = DigestUtils.sha256Hex(nuevaImagenBytes);

            return hashActual.equals(hashNueva);
        }
        catch (Exception e) {
            return false;
        }
    }

    @Override
    public void eliminarArchivo(String urlArchivo) {
        try {
            String publicId = extraerPublicIdDelUrl(urlArchivo);
            Map respuestaCloudinary = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());

            if (!respuestaCloudinary.get("result").equals("ok")) {
                throw new ErrorException409(CloudinaryConstants.MESSAGE_ERROR_DELETE);
            }
        }
        catch (Exception e) {
            throw new ErrorException409(e.getMessage());
        }
    }

    @Override
    public String extraerPublicIdDelUrl(String urlArchivo) {
        try {
            String[] urlSegmentada = urlArchivo.split("/");

            StringBuilder publicIdBuilder = new StringBuilder();

            boolean comenzar = false;

            for (int i = 0; i < urlSegmentada.length; i++) {
                String parte = urlSegmentada[i];

                if (comenzar) {
                    // Saltar la parte de versión si comienza con 'v' seguido de dígitos
                    if (parte.matches("^v\\d+$")) {
                        continue;
                    }
                    // Última parte: el archivo con extensión (ej. DNI.jpg)
                    if (i == urlSegmentada.length - 1) {
                        int punto = parte.lastIndexOf('.');
                        parte = (punto != -1) ? parte.substring(0, punto) : parte;
                        publicIdBuilder.append(parte);
                    } else {
                        publicIdBuilder.append(parte).append("/");
                    }
                }

                if (parte.equals("upload")) {
                    comenzar = true;
                }
            }

            return publicIdBuilder.toString();

        } catch (Exception e) {
            throw new ErrorException409(
                    CloudinaryConstants.MESSAGE_ERROR_EXTRACT_PUBLIC_ID
            );
        }
    }

}
