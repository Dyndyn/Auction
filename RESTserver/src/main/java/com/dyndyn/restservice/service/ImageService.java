package com.dyndyn.restservice.service;

import com.dyndyn.model.Image;
import com.dyndyn.restservice.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * The ImageService class is used to hold business
 * logic for working with the ImageRepository.
 *
 * @author Roman Dyndyn
 */
@Service
public class ImageService {
    private final ImageRepository imageRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Image getById(int id) {
        return imageRepository.getById(id);
    }

    public void insert(Image image) {
        imageRepository.add(image);
    }

    public void update(Image image) {
        imageRepository.update(image);
    }

    public void delete(int id) {
        imageRepository.remove(id);
    }
}
