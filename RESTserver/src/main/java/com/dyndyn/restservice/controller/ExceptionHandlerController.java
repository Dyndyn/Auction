package com.dyndyn.restservice.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.PersistenceException;

/**
 * The ExceptionHandlerController class is used to provide methods that handler common exceptions
 *
 * @author Roman Dyndyn
 */
@ControllerAdvice
public class ExceptionHandlerController {

    private static final Logger LOGGER = LogManager.getLogger(ExceptionHandlerController.class);

    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String dataAccessException(DataAccessException e) {
        LOGGER.error(e.getMessage(), e);

        return null;
    }

    @ExceptionHandler(PersistenceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String persistenceException(PersistenceException e) {
        LOGGER.error(e.getMessage(), e);

        return null;
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String accessDeniedException(AccessDeniedException e) {
        LOGGER.error(e.getMessage(), e);

        return null;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public String IllegalArgumentException(IllegalArgumentException e) {
        LOGGER.error(e.getMessage(), e);

        return null;
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String runtimeException(RuntimeException e) {
        LOGGER.error(e.getMessage(), e);

        return null;
    }
}