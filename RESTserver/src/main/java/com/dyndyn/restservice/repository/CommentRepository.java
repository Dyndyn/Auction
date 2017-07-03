package com.dyndyn.restservice.repository;

import com.dyndyn.model.Comment;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * The CommentRepository is used to manipulate
 * comment table.
 *
 * @author Roman Dyndyn
 */
@Repository
@Transactional
public class CommentRepository {
    @PersistenceContext
    private EntityManager em;

    public void add(Comment comment) {
        em.persist(comment);
    }

    public Comment getById(int id) {
        return em.find(Comment.class, id);
    }

    public List<Comment> getByLotId(int lotId) {
        Query query = em.createNamedQuery("comment.getByLotId", Comment.class);
        query.setParameter("lotid", lotId);
        return query.getResultList();
    }

    public void update(Comment comment) {
        em.merge(comment);
    }

    public void remove(int commentId) {
        Comment comment = em.find(Comment.class, commentId);
        em.remove(comment);
    }

}
