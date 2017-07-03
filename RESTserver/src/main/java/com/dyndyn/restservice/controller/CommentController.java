package com.dyndyn.restservice.controller;

import com.dyndyn.model.Comment;
import com.dyndyn.restservice.service.CommentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The CommentController class handles requests for comment resources.
 *
 * @author Roman Dyndyn
 */
@RestController
public class CommentController {

    private static final Logger LOGGER = LogManager.getLogger(CommentController.class);
    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @GetMapping(value = "/api/comment/{commentId}")
    public Comment getById(@PathVariable final int commentId) {
        Comment comment = commentService.getById(commentId);
        LOGGER.trace("comment with id = {} was found", commentId);
        return comment;
    }

    @GetMapping("/comment/lot/{lotId}")
    public List<Comment> getByLotId(@PathVariable int lotId) {
        List<Comment> comments = commentService.getByLotId(lotId);
        LOGGER.trace("comments with lot id = {} was found", lotId);
        return comments;
    }

    @PostMapping("/api/comment")
    public void insert(@RequestBody final Comment comment) {
        commentService.add(comment);
        LOGGER.trace("comment with user id = {} was added", comment.getUser().getId());
    }

    @PutMapping("/api/comment")
    public void update(@RequestBody final Comment comment) {
        commentService.update(comment);
        LOGGER.trace("comment with id = {} was updated", comment.getId());
    }

    @DeleteMapping("/api/comment/{commentId}")
    public void delete(@PathVariable int commentId) {
        commentService.remove(commentId);
        LOGGER.trace("comment with id = {} was deleted", commentId);
    }
}
