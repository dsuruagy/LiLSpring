package com.test.hplus.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectController {
    @GetMapping("/goToLinkedin")
    public String goOutside() {
        return "redirect:http://www.linkedin.com";
    }
}
