package com.api.intrachat.services.impl.others;

import com.api.intrachat.exceptions.errors.ErrorException400;
import com.api.intrachat.services.interfaces.others.ICloudinaryService;
import com.api.intrachat.utils.constants.ValuesCloudinary;
import com.cloudinary.Cloudinary;
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
    public String uploadFile(MultipartFile file) {
        try {
            Map<?, ?> uploadResult = cloudinary.uploader()
                    .upload(file.getBytes(), Map.of(
                            ValuesCloudinary.KEY_LOCATION_UPLOAD_FILE,
                            ValuesCloudinary.VALUE_LOCATION_UPLOAD_FILE
                    ));

            return uploadResult.get(
                    ValuesCloudinary.SECURE_URL_CLOUDINARY
            ).toString();

        } catch (IOException e) {
            throw new ErrorException400(ValuesCloudinary.MESSAGE_ERROR_UPLOAD);
        }
    }

    @Override
    public boolean newFileEqualsCurrentFile(MultipartFile newFile, String urlFileCurrent) {
        return false;
    }

    @Override
    public void deleteFile(String urlFile) {

    }

    @Override
    public String extractPublicIdFromUrlFile(String urlFile) {
        return "";
    }

}
