package com.test.hplus.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ApplicationExceptionHandler {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(ApplicationException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public String ExceptionHandler() {
        LOGGER.error("in Global exception handler");
        return "error";
    }
}
