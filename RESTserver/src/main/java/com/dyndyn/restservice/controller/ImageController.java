package com.dyndyn.restservice.controller;

import com.dyndyn.model.Image;
import com.dyndyn.restservice.service.ImageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * The class contains methods to add, read and delete image from database using REST Service
 *
 * @author Roman Dyndyn
 */

@RestController
@RequestMapping
public class ImageController {

    public static final Logger LOGGER = LogManager.getLogger(ImageController.class);
    private ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    /**
     * Returns image by id
     *
     * @param imageId id of image
     * @return image
     */

    @GetMapping("/image/{imageId}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public Image getImageById(@PathVariable("imageId") int imageId) {

        Image image = imageService.getById(imageId);
        LOGGER.trace("Image with id = {} was found", imageId);
        return image;
    }

    /**
     * Uploads image to DataBase
     */

    @PostMapping("/api/image")
    @ResponseStatus(value = HttpStatus.OK)
    public Integer uploadImageObject(@RequestBody Image image) {
        imageService.insert(image);
        LOGGER.trace("Image {} was successfully saved to DataBase", image.getId());
        return image.getId();
    }

    /**
     * Deletes image from DataBase
     *
     * @param imageId id of image
     */

    @DeleteMapping("/api/image/{imageId}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteImage(@PathVariable("imageId") int imageId) {
        imageService.delete(imageId);
        LOGGER.info("Image with id {} was deleted", imageId);
    }

    /**
     * Updates image
     */

    @PutMapping("/api/image")
    @ResponseStatus(value = HttpStatus.OK)
    public void update(@RequestBody Image image) {
        int id = image.getId();
        imageService.update(image);
        LOGGER.trace("Image with id {} was updated", id);
    }

}