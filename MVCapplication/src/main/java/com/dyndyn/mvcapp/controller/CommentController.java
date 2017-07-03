package com.dyndyn.mvcapp.controller;

import com.dyndyn.model.Comment;
import com.dyndyn.model.User;
import com.dyndyn.mvcapp.service.CommentService;
import com.dyndyn.mvcapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by dyndyn on 25.06.2017.
 */
@Controller
public class CommentController {

    private CommentService commentService;
    private UserService userService;

    @Autowired
    public CommentController(CommentService commentService, UserService userService) {
        this.commentService = commentService;
        this.userService = userService;
    }

    @PostMapping("/comment")
    @ResponseBody
    public String addComment(@Validated @ModelAttribute Comment comment, final BindingResult result,
                             final HttpServletResponse response){
        User user;
        if((user=userService.getCurrentUserFromSession()) == null){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return "You must be authorized";
        }
        if (result.hasErrors()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            StringBuilder message = new StringBuilder();
            int i = 0;
            for (FieldError error : result.getFieldErrors()) {
                if (i++ > 0) {
                    message.append(",\r\n");
                }
                message.append(error.getDefaultMessage());
            }
            return message.toString();
        }
        commentService.addComment(comment);
        response.setStatus(HttpServletResponse.SC_OK);
        return user.getName();
    }
}
