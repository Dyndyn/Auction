package com.dyndyn.mvcapp.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * The ExceptionHandlerController class is used to provide methods that handle common exceptions
 *
 * @author Roman Dyndyn
 */
@ControllerAdvice
public class ExceptionHandlerController {
    private static final Logger LOGGER = LogManager.getLogger();

    @Value("login.unauthorized")
    private String unauthorizedAccess;

    @ExceptionHandler(HttpClientErrorException.class)
    public String handleRestClientException(Model model, HttpClientErrorException e, final RedirectAttributes redirectAttributes) {

        HttpStatus statusCode = e.getStatusCode();
        LOGGER.error(e.getMessage(), e);

        if (statusCode.equals(HttpStatus.FORBIDDEN)) {
            return "403";
        }
        if (statusCode.equals(HttpStatus.NOT_FOUND)) {
            return "404";
        }
        if (statusCode.equals(HttpStatus.UNAUTHORIZED)) {
            redirectAttributes.addFlashAttribute("informationMessage", unauthorizedAccess);
            return "redirect:/login";
        }

        //by default, return general error page
        return "generalError";
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public String handleRestServerException(HttpServerErrorException e) {
        LOGGER.error(e.getMessage(), e);
        return "generalError";
    }
}