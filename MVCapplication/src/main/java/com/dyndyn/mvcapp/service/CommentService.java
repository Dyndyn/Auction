package com.dyndyn.mvcapp.service;

import com.dyndyn.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
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

    private RestTemplate restTemplate;
    private UserService userService;

    @Autowired
    public CommentService(RestTemplate restTemplate, UserService userService) {
        this.restTemplate = restTemplate;
        this.userService = userService;
    }

    public void addComment(Comment comment){
        comment.setUser(userService.getCurrentUserFromSession());
        restTemplate.postForObject(commentUrl, comment, Comment.class);
    }

    public List<Comment> getByLotId(final int lotId){
        List<Comment> comments = restTemplate.exchange(commentLotUrl, HttpMethod.GET,
                null, new ParameterizedTypeReference<List<Comment>>() {
                }, lotId).getBody();


        comments.forEach(comment -> {
            System.out.println(comment.getDate());
            comment.setDate(new Timestamp(
                    (System.currentTimeMillis() - comment.getDate().getTime()) / (1000  * 60 * 60 * 24)));

        });

        return comments;
    }
}
