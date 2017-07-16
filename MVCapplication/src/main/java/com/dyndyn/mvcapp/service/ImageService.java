package com.dyndyn.mvcapp.service;

import com.dyndyn.model.Image;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class ImageService {
    public static final Logger LOGGER = LogManager.getLogger(ImageService.class);

    @Value("${application.rest.image}")
    private String imageUrl;
    @Value("${application.rest.secured.image}")
    private String imageSecuredUrl;
    @Value("${application.rest.secured.image.delete}")
    private String imageDeleteSecuredUrl;

    private RestTemplate restTemplate;
    private UserService userService;

    @Autowired
    public ImageService(RestTemplate restTemplate, UserService userService) {
        this.restTemplate = restTemplate;
        this.userService = userService;
    }

    public Integer addImage(Image image) {
        try {
            image.onCreate();
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return restTemplate.postForObject(imageSecuredUrl, image, Integer.class);
    }

    public Image getImageById(int imageId){
        return restTemplate.getForObject(imageUrl, Image.class, imageId);
    }

    public void updateImage(Image image){
        restTemplate.put(imageSecuredUrl, image);
    }


    public void deleteImage(int imageId){
        restTemplate.delete(imageDeleteSecuredUrl, imageId);
    }
}
