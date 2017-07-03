package com.dyndyn.restservice.service;

import com.dyndyn.model.Lot;
import com.dyndyn.model.Rating;
import com.dyndyn.restservice.repository.LotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The LotService class is used to hold business
 * logic for working with the LotRepository.
 *
 * @author Roman Dyndyn
 */
@Service
public class LotService {

    private LotRepository lotRepository;

    @Autowired

    public LotService(LotRepository lotRepository) {
        this.lotRepository = lotRepository;
    }

    public void add(Lot lot) {
        lotRepository.add(lot);
    }

    public Lot getById(int id) {
        return lotRepository.getById(id);
    }

    public List<Lot> getAll() {
        return lotRepository.getAll();
    }

    public List<Lot> getByUserId(int userId) {
        return lotRepository.getByUserId(userId);
    }

    public List<Lot> getByCategoryId(int categoryId) {
        return lotRepository.getByCategoryId(categoryId);
    }

    public void update(Lot lot) {
        lotRepository.update(lot);
    }

    public void remove(int lotId) {
        lotRepository.remove(lotId);
    }

    public void add(Rating rating){
        lotRepository.add(rating);
    }
}
