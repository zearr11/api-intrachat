package com.api.intrachat.services.interfaces;

import org.springframework.web.multipart.MultipartFile;

public interface ICloudinaryService {

    String uploadFile(MultipartFile archivo);
    boolean newFileEqualsCurrentFile(MultipartFile newFile, String urlFileCurrent);
    void deleteFile(String urlFile);
    String extractPublicIdFromUrlFile(String urlFile);
}
