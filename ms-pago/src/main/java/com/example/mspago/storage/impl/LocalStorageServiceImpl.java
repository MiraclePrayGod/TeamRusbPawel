package com.example.mspago.storage.impl;

import com.example.mspago.storage.StorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class LocalStorageServiceImpl implements StorageService {

    private final Path root = Paths.get("comprobantes");

    @Override
    public String store(MultipartFile file) {
        try {
            if (!Files.exists(root)) Files.createDirectories(root);
            Path destino = root.resolve(file.getOriginalFilename());
            Files.copy(file.getInputStream(), destino);
            return destino.toString();              // Devuelves la URL o path
        } catch (IOException e) {
            throw new RuntimeException("No se pudo guardar archivo", e);
        }
    }
}