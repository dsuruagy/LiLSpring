package com.test.hplus.controllers;

import com.test.hplus.beans.User;
import com.test.hplus.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {
    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/registeruser")
    public String registerUser(@ModelAttribute("newuser")User user, Model model) {
        LOGGER.debug("in registration controller");
        userRepository.save(user);
        model.addAttribute("userRegisteredMsg", "User registered successfully");
        return "login";
    }
}
