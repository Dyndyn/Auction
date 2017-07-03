package com.dyndyn.restservice.repository;

import com.dyndyn.model.Category;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * The CategoryRepository is used to manipulate
 * category table.
 *
 * @author Roman Dyndyn
 */
@Repository
@Transactional
public class CategoryRepository {
    @PersistenceContext
    private EntityManager em;

    public void add(Category category) {
        em.persist(category);
    }

    public Category getById(int id) {
        return em.find(Category.class, id);
    }

    public List<Category> getAll() {
        Query query = em.createNamedQuery("category.getAll", Category.class);
        return query.getResultList();
    }

    public void update(Category category) {
        em.merge(category);
    }

    public void remove(int categoryId) {
        Category category = em.find(Category.class, categoryId);
        em.remove(category);
    }
}
