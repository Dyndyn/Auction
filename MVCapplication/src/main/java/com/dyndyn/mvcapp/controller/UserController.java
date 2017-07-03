package com.dyndyn.mvcapp.controller;

import com.dyndyn.model.User;
import com.dyndyn.mvcapp.dto.UserRegistrationForm;
import com.dyndyn.mvcapp.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Created by dyndyn on 21.06.2017.
 */
@Controller()
public class UserController {
    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    @Value("registration.successful")
    private String registrationSuccessfulMessage;

    @Value("registration.generalError")
    private String generalErrorMessage;

    @Value("registration.alreadyExists")
    private String alreadyExists;

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Creates empty {@link User} and puts it with into model.
     * Displays user registration page.
     */
    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("registrationForm", new UserRegistrationForm());

        return "register";
    }

    /**
     * Handles user registration process. In case of errors (such as already existed account,
     * incorrect account type etc.) redirects to register page with displaying error message.
     *
     * @param registrationForm   an {@link ModelAttribute} filled in with user's data
     * @param result             validation result
     * @param redirectAttributes attributes with custom messages to be displayed on the page
     * @return view name
     */
    @PostMapping("/register")
    public String postRegisterPage(@Validated @ModelAttribute("registrationForm") UserRegistrationForm registrationForm,
                                   BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "register";
        }

        if (!registrationForm.getPassword().equals(registrationForm.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("errorMessage", generalErrorMessage);
            return "redirect:/register";
        }

        HttpStatus status = userService.performRegistration(registrationForm);
        if (status.equals(HttpStatus.OK)) {
            redirectAttributes.addFlashAttribute("successMessage", registrationSuccessfulMessage);
            LOGGER.info("User {} has been successfully registered");
            return "redirect:/login";
        } else if(status.equals(HttpStatus.RESET_CONTENT)){
            redirectAttributes.addFlashAttribute("errorMessage", alreadyExists);
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", generalErrorMessage);
        }

        return "redirect:/register";
    }


}
