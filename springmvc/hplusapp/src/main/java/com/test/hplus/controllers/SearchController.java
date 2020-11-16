package com.test.hplus.controllers;

import com.test.hplus.beans.Product;
import com.test.hplus.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchController {
    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/searchProduct")
    public String search(@RequestParam("searchParam") String searchParam,
                         Model model) {
        LOGGER.debug("in search controller");
        LOGGER.debug("searchParam: {}", searchParam);

        List<Product> products = productRepository.searchByName(searchParam);
        model.addAttribute("products", products);

        return "search";
    }
}
