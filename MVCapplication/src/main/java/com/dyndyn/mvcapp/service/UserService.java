package com.dyndyn.mvcapp.service;

import com.dyndyn.model.Role;
import com.dyndyn.model.User;
import com.dyndyn.mvcapp.dto.UserRegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

/**
 * Created by dyndyn on 21.06.2017.
 */
@Service
public class UserService {
    @Value("${application.rest.user.getById}")
    private String restUserByIdUrl;

    @Value("${application.rest.user.current}")
    private String getCurrentUserURL;

    @Value("${application.rest.register}")
    private String registerUrl;

    private RestTemplate restTemplate;

    @Autowired
    public UserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public User getUserById(int id) {
        User user = restTemplate.exchange(restUserByIdUrl, HttpMethod.GET,
                null, new ParameterizedTypeReference<User>() {
                }, id).getBody();
        return user;
    }

    /**
     * Method provides access to current logged in {@link User} instance.
     *
     * @return current User instance
     */
    public User getCurrentUser() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = restTemplate.getForObject(getCurrentUserURL, User.class);
            session.setAttribute("user", user);
        }
        return user;
    }

    /**
     * Method provides access to current {@link User} instance.
     *
     * @return current User instance
     */
    public User getCurrentUserFromSession() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession();
        User user = (User) session.getAttribute("user");
        return user;
    }

    /**
     * Performs user registration on RESTful service.
     *
     * @param registrationForm filled in user instance
     * @return true if user has been successfully registered
     */
    public HttpStatus performRegistration(UserRegistrationForm registrationForm) {
        User user = new User();
        user.setEmail(registrationForm.getEmail());
        user.setName(registrationForm.getName());
        user.setPassword(registrationForm.getPassword());
        user.setAddress(registrationForm.getAddress());
        user.setEnabled(true);
        user.setRole(Role.ROLE_USER);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(registerUrl, user, String.class);

        return responseEntity != null ? responseEntity.getStatusCode() : HttpStatus.BAD_REQUEST;
    }
}
