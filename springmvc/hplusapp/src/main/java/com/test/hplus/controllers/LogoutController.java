package com.test.hplus.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class LogoutController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        LOGGER.debug("Ending user session");
        httpSession.invalidate();

        return "login";
    }
}
