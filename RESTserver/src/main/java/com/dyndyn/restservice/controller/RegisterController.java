package com.dyndyn.restservice.controller;

import com.dyndyn.model.User;
import com.dyndyn.restservice.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private static final Logger LOGGER = LogManager.getLogger(RegisterController.class);
    private UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> performRegistration(@RequestBody User user) {

        if (user == null || user.getRole() == null || StringUtils.isBlank(user.getEmail()) ||
                StringUtils.isBlank(user.getName()) || StringUtils.isBlank(user.getPassword()) ||
                StringUtils.isBlank(user.getAddress())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User alreadyRegisteredUser = userService.getByEmail(user.getEmail());
        if (alreadyRegisteredUser != null) {
            return new ResponseEntity<>(HttpStatus.RESET_CONTENT);
        }

        userService.add(user);
        LOGGER.info("User successfully registered: " + user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
