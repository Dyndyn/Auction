package com.dyndyn.restservice.controller;

import com.dyndyn.model.User;
import com.dyndyn.restservice.security.authentication.AuthenticatedUserProxy;
import com.dyndyn.restservice.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The UserController class handles requests for user resources.
 *
 * @author Roman Dyndyn
 */

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    private static final Logger LOGGER = LogManager.getLogger(UserController.class);
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/{userId}")
    public User getById(@PathVariable final int userId){
        User user = userService.getById(userId);
        LOGGER.trace("user with id = {} was found", userId);
        return user;
    }

    @GetMapping()
    public List<User> getAll(){
        List<User> users = userService.getAll();
        LOGGER.trace("All users were found");
        return users;
    }

    @PostMapping
    public void insert(@RequestBody User user) {
        userService.add(user);
        LOGGER.info("User successfully registered: " + user);
    }

    @PutMapping
    public void update(@RequestBody final User user){
        userService.update(user);
        LOGGER.trace("user with id = {} was updated", user.getId());
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable int userId){
        userService.remove(userId);
        LOGGER.trace("user with id = {} was deleted", userId);
    }

    /**
     * Retrieves current principal from Spring's SecurityHolder and returns it as a response
     *
     * @return current logged in user entity
     */
    @GetMapping(value = "/security/current")
    public User testController() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated()) {
            AuthenticatedUserProxy userProxy = (AuthenticatedUserProxy) auth;

            return userProxy.getUser();
        }

        return null;
    }
}
