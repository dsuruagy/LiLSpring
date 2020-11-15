package com.test.hplus.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
}
