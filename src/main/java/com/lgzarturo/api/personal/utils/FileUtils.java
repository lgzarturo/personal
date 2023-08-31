package com.lgzarturo.api.personal.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Slf4j
public class FileUtils {

    public static String uploadFile(MultipartFile file, String dir) {
        String fileName = Helpers.randomAlphanumeric(8) + "-" + file.getOriginalFilename();
        String filePath = dir + fileName;
        try {
            File dest = new File(filePath);
            file.transferTo(dest);
            return fileName;
        } catch (Exception e) {
            log.error("Error uploading file: {}", e.getMessage());
            throw new RuntimeException("Error uploading file: " + fileName);
        }
    }

    public static Boolean removeFile(String uri, String dir) {
        File file = new File(dir + uri);
        if (!file.exists()) {
            log.error("File not found: {}", uri);
            throw new RuntimeException("File not found: " + uri);
        }
        return file.delete();
    }
}
