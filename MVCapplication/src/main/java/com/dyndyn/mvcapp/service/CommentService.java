package com.dyndyn.mvcapp.service;

import com.dyndyn.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by dyndyn on 25.06.2017.
 */
@Service
public class CommentService {

    @Value("${application.rest.secured.comment}")
    private String commentUrl;
    @Value("${application.rest.comment.lot}")
    private String commentLotUrl;
    @Value("${application.broadcast.lot}")
    private String broadcastLot;

    private RestTemplate restTemplate;
    private UserService userService;
    private SimpMessagingTemplate messaging;

    @Autowired
    public CommentService(RestTemplate restTemplate, UserService userService, SimpMessagingTemplate simpMessagingTemplate) {
        this.restTemplate = restTemplate;
        this.userService = userService;
        this.messaging = simpMessagingTemplate;
    }

    public void addComment(Comment comment) {
        comment.setUser(userService.getCurrentUserFromSession());
        restTemplate.postForObject(commentUrl, comment, Comment.class);
        broadcastComment(comment);
    }

    public List<Comment> getByLotId(final int lotId) {
        List<Comment> comments = restTemplate.exchange(commentLotUrl, HttpMethod.GET,
                null, new ParameterizedTypeReference<List<Comment>>() {
                }, lotId).getBody();


        comments.forEach(comment -> {
            System.out.println(comment.getDate());
            comment.setDate(new Timestamp(
                    (System.currentTimeMillis() - comment.getDate().getTime()) / (1000 * 60 * 60 * 24)));

        });

        return comments;
    }

    public void broadcastComment(Comment comment) {
        messaging.convertAndSend(broadcastLot + comment.getLot().getId(), comment);
    }
}
