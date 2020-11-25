package com.test.hplus.controllers;

import com.test.hplus.beans.Login;
import com.test.hplus.beans.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Arrays;
import java.util.List;

@Controller
public class HomeController {
    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/home")
    public String goHome() {
        LOGGER.debug("in home controller");
        return "index";
    }

    @GetMapping("/goToSearch")
    public String goToSearch() {
        LOGGER.debug("going to search page");
        return "search";
    }

    @GetMapping("/goToLogin")
    public String goToLogin() {
        LOGGER.debug("going to login page");
        return "login";
    }

    @GetMapping("/goToRegistration")
    public String goToRegistration() {
        LOGGER.debug("going to register page");
        return "register";
    }
}
