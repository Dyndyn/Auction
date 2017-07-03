package com.dyndyn.restservice.service;

import com.dyndyn.model.Comment;
import com.dyndyn.restservice.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The CommentService class is used to hold business
 * logic for working with the CommentRepository.
 *
 * @author Roman Dyndyn
 */
@Service
public class CommentService {

    private CommentRepository commentRepository;

    @Autowired

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public void add(Comment comment) {
        commentRepository.add(comment);
    }

    public Comment getById(int id) {
        return commentRepository.getById(id);
    }

    public List<Comment> getByLotId(int lotId) {
        return commentRepository.getByLotId(lotId);
    }

    public void update(Comment comment) {
        commentRepository.update(comment);
    }

    public void remove(int commentId) {
        commentRepository.remove(commentId);
    }
}
