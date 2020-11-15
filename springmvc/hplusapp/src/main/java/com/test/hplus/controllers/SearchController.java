package com.test.hplus.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SearchController {
    private Logger LOGGER = LoggerFactory.getLogger(SearchController.class);

    @GetMapping("/search")
    public String search() {
        LOGGER.debug("in search controller");
        return "search";
    }
}
