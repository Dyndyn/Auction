package com.dyndyn.restservice.repository;

import com.dyndyn.model.Lot;
import com.dyndyn.model.Rating;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The LotRepository is used to manipulate
 * lot table.
 *
 * @author Roman Dyndyn
 */
@Repository
@Transactional
public class LotRepository {

    @PersistenceContext
    private EntityManager em;

    public void add(Lot lot) {
        em.persist(lot);
    }

    public Lot getById(int id) {
        Query query = em.createNamedQuery("lot.getById");
        query.setParameter("id", id);
        List<Object[]> result = query.getResultList();
        Object[] data = result.get(0);
        return parseObjects(data);
    }

    public List<Lot> getAll() {
        Query query = em.createNamedQuery("lot.getAll");
        List<Object[]> result = query.getResultList();

        return result.stream().map(this::parseObjects).collect(Collectors.toList());
    }

    public List<Lot> getByUserId(int userId) {
        Query query = em.createNamedQuery("lot.getByUserId");
        query.setParameter("userid", userId);
        List<Object[]> result = query.getResultList();
        return result.stream().map(this::parseObjects).collect(Collectors.toList());
    }

    public List<Lot> getByCategoryId(int categoryId) {
        Query query = em.createNamedQuery("lot.getByCategoryId");
        query.setParameter("categoryid", categoryId);
        List<Object[]> result = query.getResultList();
        return result.stream().map(this::parseObjects).collect(Collectors.toList());
    }

    public void update(Lot lot) {
        em.merge(lot);
    }

    public void enabled(Lot lot) {
        Lot temp = em.find(Lot.class, lot.getId());
        temp.setEnabled(lot.isEnabled());
        em.merge(temp);
    }

    public void remove(int lotId) {
        Lot lot = em.find(Lot.class, lotId);
        em.remove(lot);
    }

    public void add(Rating rating) {
        em.merge(rating);
    }

    private Lot parseObjects(Object[] objects){
        Lot lot = (Lot) objects[0];
        lot.setReviews((Long) objects[1]);
        lot.setRating(objects[2] != null ? (Double) objects[2] : 0.0);
        return lot;
    }
}
