package com.dyndyn.mvcapp.service;

import com.dyndyn.model.Lot;
import com.dyndyn.model.Rating;
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
    @Value("${application.rest.lot}")
    private String restLotUrl;
    @Value("${application.rest.secured.lot}")
    private String restSecuredLotUrl;
    @Value("${application.rest.rating}")
    private String restRatingUrl;

    private RestTemplate restTemplate;
    private UserService userService;

    @Autowired
    public LotService(RestTemplate restTemplate, UserService userService) {
        this.restTemplate = restTemplate;
        this.userService = userService;
    }

    public Lot getLotById(final int lotId){
        Lot lot = restTemplate.exchange(restLotByIdUrl, HttpMethod.GET,
                null, new ParameterizedTypeReference<Lot>() {
                }, lotId).getBody();
        encodeImage(lot);
        return lot;
    }

    public List<Lot> getLotsByCategoryId(final int categoryId){
        List<Lot> lots = restTemplate.exchange(restLotsByCategoryIdUrl, HttpMethod.GET,
                null, new ParameterizedTypeReference<List<Lot>>() {
                }, categoryId).getBody();
        lots.forEach(LotService::encodeImage);
        return lots;
    }

    public List<Lot> getAllLots(){
        List<Lot> lots = restTemplate.exchange(restLotUrl, HttpMethod.GET,
                null, new ParameterizedTypeReference<List<Lot>>() {
                }).getBody();
        lots.forEach(LotService::encodeImage);
        return lots;
    }

    public void addLot(Lot lot){
        lot.setUser(userService.getCurrentUser());
        try {
            if(lot.getImage().getMultipartFile().getBytes().length == 0){
                lot.setImage(null);
            } else {
                lot.getImage().onCreate();
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(),e);
        }
        restTemplate.postForObject(restSecuredLotUrl, lot, Lot.class);
    }

    public void addRating(Rating rating){
        rating.getId().setUserId(userService.getCurrentUser().getId());
        restTemplate.postForObject(restRatingUrl, rating, Rating.class);
    }

    private static void encodeImage(Lot lot){
        try {
            if(lot.getImage() != null) {
                lot.getImage().encodeBase64();
            }
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

}
