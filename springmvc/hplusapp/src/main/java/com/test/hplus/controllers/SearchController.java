package com.test.hplus.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {
    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/searchProduct")
    public String search(@RequestParam("searchParam") String searchParam,
                         Model model) {
        LOGGER.debug("in search controller");
        LOGGER.debug("searchParam: {}", searchParam);

        return "search";
    }
}
