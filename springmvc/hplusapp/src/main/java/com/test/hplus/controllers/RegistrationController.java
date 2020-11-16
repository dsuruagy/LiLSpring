package com.test.hplus.controllers;

import com.test.hplus.beans.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {
    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/registeruser")
    public String registerUser(@ModelAttribute("newuser")User user) {
        LOGGER.debug("in registration controller");
        return "login";
    }
}
