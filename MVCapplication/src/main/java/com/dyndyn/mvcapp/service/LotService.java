package com.dyndyn.mvcapp.service;

import com.dyndyn.model.Category;
import com.dyndyn.model.Lot;
import com.dyndyn.model.Rating;
import com.dyndyn.mvcapp.dto.LotAddingForm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dyndyn on 21.06.2017.
 */
@Service
public class LotService {

    private static final Logger LOGGER = LogManager.getLogger(LotService.class);
    @Value("${application.rest.lot.getById}")
    private String restLotByIdUrl;
    @Value("${application.rest.lot.getByCategoryId}")
    private String restLotsByCategoryIdUrl;
    @Value("${application.rest.lot.getByUserId}")
    private String restLotsByUserIdUrl;
    @Value("${application.rest.lot}")
    private String restLotUrl;
    @Value("${application.rest.secured.lot}")
    private String restSecuredLotUrl;
    @Value("${application.rest.rating}")
    private String restRatingUrl;
    @Value("${application.rest.lot.enabled}")
    private String restLotEnabledUrl;

    private RestTemplate restTemplate;
    private UserService userService;
    private ImageService imageService;

    @Autowired
    public LotService(RestTemplate restTemplate, UserService userService, ImageService imageService) {
        this.restTemplate = restTemplate;
        this.userService = userService;
        this.imageService = imageService;
    }

    public Lot getLotById(final int lotId){
        Lot lot = restTemplate.exchange(restLotByIdUrl, HttpMethod.GET,
                null, new ParameterizedTypeReference<Lot>() {
                }, lotId).getBody();
        return lot;
    }

    public List<Lot> getLotsByCategoryId(final int categoryId){
        List<Lot> lots = restTemplate.exchange(restLotsByCategoryIdUrl, HttpMethod.GET,
                null, new ParameterizedTypeReference<List<Lot>>() {
                }, categoryId).getBody();
        return lots;
    }

    public List<Lot> getLotsByUserId(){
        List<Lot> lots = restTemplate.exchange(restLotsByUserIdUrl, HttpMethod.GET,
                null, new ParameterizedTypeReference<List<Lot>>() {
                }, userService.getCurrentUser().getId()).getBody();
        return lots;
    }

    public List<Lot> getAllLots(){
        List<Lot> lots = restTemplate.exchange(restLotUrl, HttpMethod.GET,
                null, new ParameterizedTypeReference<List<Lot>>() {
                }).getBody();
        return lots;
    }

    public void addLot(Lot lot){
        lot.setUser(userService.getCurrentUser());
        restTemplate.postForObject(restSecuredLotUrl, lot, Lot.class);
    }

    public void addLot(LotAddingForm lotForm){
        Lot lot =new Lot();
        Integer imageId = null;
        if(lotForm.getImage() != null && lotForm.getImage().getMultipartFile() != null
                && !lotForm.getImage().getMultipartFile().isEmpty()) {
            imageId = imageService.addImage(lotForm.getImage());
        }

        lot.setImageId(imageId);
        lot.setName(lotForm.getName());
        lot.setCategories(lotForm.getCategories());
        lot.setDescription(lotForm.getDescription());
        lot.setDiff(lotForm.getDiff());
        lot.setPrice(lotForm.getPrice());
        lot.setUser(userService.getCurrentUser());

        restTemplate.postForObject(restSecuredLotUrl, lot, Lot.class);
    }

    public void enable(Lot lot){
        restTemplate.put(restLotEnabledUrl, lot);
    }

    public void addRating(Rating rating){
        rating.getId().setUserId(userService.getCurrentUser().getId());
        restTemplate.postForObject(restRatingUrl, rating, Rating.class);
    }

}
