package com.dyndyn.mvcapp.controller;

import com.dyndyn.model.Image;
import com.dyndyn.mvcapp.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/image")
public class ImageController {

    private ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void getImage(@RequestParam("imageId") Integer imageId, HttpServletResponse response) throws IOException {
        Image image = imageService.getImageById(imageId);

        if (image != null) {
            response.setContentType(image.getContentType());
            response.getOutputStream().write(image.getImageData());
            response.getOutputStream().close();
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadImage(@ModelAttribute Image image){
        imageService.addImage(image);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateImage(@ModelAttribute Image image){
        imageService.updateImage(image);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteImage(@RequestParam("imageId") int imageId){
        imageService.deleteImage(imageId);
    }
}
