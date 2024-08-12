package com.chikacow.pet_project.service;

import jakarta.annotation.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface StorageService {

    void init();

    String store(MultipartFile file);

    Path load(String filename);

    Resource loadAsResource(String filename);

    void delete(String filename);
}

