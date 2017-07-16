package com.dyndyn.restservice.controller;

import com.dyndyn.model.Lot;
import com.dyndyn.model.Rating;
import com.dyndyn.restservice.service.LotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * The LotController class handles requests for lot resources.
 *
 * @author Roman Dyndyn
 */
@RestController
public class LotController {

    private static final Logger LOGGER = LogManager.getLogger(LotController.class);
    private LotService lotService;

    @Autowired
    public LotController(LotService lotService) {
        this.lotService = lotService;
    }

    @GetMapping(value = "/lot/{lotId}")
    public Lot getById(@PathVariable final int lotId) {

        Lot lot = lotService.getById(lotId);
        LOGGER.trace("lot with id = {} was found", lotId);
        return lot;
    }

    @GetMapping(value = "/api/lot/user/{userId}")
    public List<Lot> getByUserId(@PathVariable final int userId) {

        List<Lot> lots = lotService.getByUserId(userId);
        LOGGER.trace("lots with user id = {} were found", userId);
        return lots;
    }

    @GetMapping(value = "/lot/category/{categoryId}")
    public List<Lot> getByCategoryId(@PathVariable final int categoryId) {

        List<Lot> lots = lotService.getByCategoryId(categoryId);
        LOGGER.trace("lots with category id = {} were found", categoryId);
        return lots;
    }

    @GetMapping("/lot")
    public List<Lot> getAll() {
        List<Lot> lots = lotService.getAll();
        LOGGER.trace("all lots were found");
        return lots;
    }

    @PostMapping("/api/lot")
    public void insert(@RequestBody final Lot lot) {
        lotService.add(lot);
        LOGGER.trace("lot with name = {} was added", lot.getName());
    }

    @PutMapping("/api/lot")
    public void update(@RequestBody final Lot lot) {
        lotService.update(lot);
        LOGGER.trace("lot with id = {} was updated", lot.getId());
    }

    @PutMapping("/api/lot/enabled")
    public void enabled(@RequestBody final Lot lot) {
        lotService.enabled(lot);
        LOGGER.info("lot with id = {} was updated, enabled = " + lot.isEnabled(), lot.getId());
    }

    @DeleteMapping("/api/lot/{lotId}")
    public void delete(@PathVariable int lotId) {
        lotService.remove(lotId);
        LOGGER.trace("lot with id = {} was deleted", lotId);
    }

    @PostMapping("/api/rating")
    public void insertOrUpdate(@RequestBody final Rating rating) {
        lotService.add(rating);
    }
}
