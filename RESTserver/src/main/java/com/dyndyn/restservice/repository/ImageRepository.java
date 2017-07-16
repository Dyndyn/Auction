package com.dyndyn.restservice.repository;

import com.dyndyn.model.Image;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * The ImageRepository is used to manipulate
 * image table.
 *
 * @author Roman Dyndyn
 */

@Repository
@Transactional
public class ImageRepository {


    @PersistenceContext
    private EntityManager em;

    public void add(Image image) {
        em.persist(image);
    }

    public Image getById(int id) {
        return em.find(Image.class, id);
    }

    public void update(Image image) {
        em.merge(image);
    }

    public void remove(int imageId) {
        Image image = em.find(Image.class, imageId);
        em.remove(image);
    }
}
