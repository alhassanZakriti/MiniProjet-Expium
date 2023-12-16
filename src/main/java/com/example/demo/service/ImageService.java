package com.example.demo.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Image;
import com.example.demo.repository.ImageRepo;
import com.example.demo.service.tools.ImageUtils;

@Service
public class ImageService {
    @Autowired
    private ImageRepo repo;

    public Image uploadImage(MultipartFile file) throws IOException {
        Image image = Image.builder()
                .type(file.getContentType())
                .picture(ImageUtils.compressImage(file.getBytes()))
                .build();

        return repo.save(image);
    }
}
