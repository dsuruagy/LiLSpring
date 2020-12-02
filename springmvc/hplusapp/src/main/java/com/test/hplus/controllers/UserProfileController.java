package com.test.hplus.controllers;

import com.test.hplus.beans.Login;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class UserProfileController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/userprofile")
    public String getUserProfile(@SessionAttribute Login login, Model model){
        LOGGER.debug("User name session attribute: {}", login.getUsername());

        return "profile";
    }
}
