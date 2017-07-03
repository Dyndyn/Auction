package com.dyndyn.restservice.repository;

import com.dyndyn.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * The UserRepository is used to manipulate
 * user table.
 *
 * @author Roman Dyndyn
 */

@Repository
@Transactional
public class UserRepository {

    @PersistenceContext
    private EntityManager em;

    public void add(User user) {
        em.persist(user);
    }

    public User getById(int id) {
        return em.find(User.class, id);
    }

    public List<User> getAll() {
        Query query = em.createNamedQuery("user.getAll", User.class);
        return query.getResultList();
    }

    public void update(User user) {
        em.merge(user);
    }

    public void remove(int userId) {
        User user = em.find(User.class, userId);
        em.remove(user);
    }

    public User getByEmail(String email){
        TypedQuery<User> query = em.createNamedQuery("user.getByEmail", User.class);
        query.setParameter("email", email);
        return query.getSingleResult();
    }
}
