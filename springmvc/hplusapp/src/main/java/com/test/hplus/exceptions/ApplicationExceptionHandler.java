package com.test.hplus.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;

@ControllerAdvice
public class ApplicationExceptionHandler {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler({ApplicationException.class, AsyncRequestTimeoutException.class})
    public String ExceptionHandler() {
        LOGGER.error("in Global exception handler");
        return "error";
    }

    @ExceptionHandler(LoginFailureException.class)
    public ResponseEntity LoginExceptionHandler() {
        LOGGER.error("in LoginExceptionHandler");
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
