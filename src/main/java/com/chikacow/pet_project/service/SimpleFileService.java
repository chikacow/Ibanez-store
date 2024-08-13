package com.chikacow.pet_project.service;

import com.chikacow.pet_project.config.AppConfig;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class SimpleFileService {


    public String handleFileUpload(MultipartFile file, String uploadDir) {
        try {
            if (file.isEmpty()) {
                System.out.println("file not uploaded by user");
            }
            Path basePath = Paths.get(AppConfig.IMAGE_FILE);

            if (!Files.exists(basePath)) {
                //Files.createDirectories(basePath);
                System.out.println("the heck is base dir");
            }
            System.out.println("beautiful base dir");

            Path uploadPath = basePath.resolve(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = file.getOriginalFilename();
            if (fileName == null || fileName.isEmpty()) {
                throw new IllegalArgumentException("Filename must not be empty");
            }

            fileName = System.currentTimeMillis() + "-" + fileName;

            //create correct path n a storage to put file to
            Path filePath = uploadPath.resolve(fileName);

            //saving
            Files.copy(file.getInputStream(), filePath);

            System.out.println("File uploaded successfully: " + fileName);
            System.out.println("File path: " + filePath);

            return fileName;

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to save file", e);
        }



    }

    public String handleDeleteFile(String fileName, String findDir) {
        Path basePath = Paths.get(AppConfig.IMAGE_FILE);
        Path folderPath = basePath.resolve(findDir);
        Path filePath = folderPath.resolve(fileName);

        System.out.println("path: " + filePath);
        try {
            Files.deleteIfExists(filePath);
            System.out.println("File delete successfully");
        } catch (Exception e) {
            System.out.println("Error occurred while deleting the file: " + e.getMessage());
        }

        return filePath.toString();
    }
}
