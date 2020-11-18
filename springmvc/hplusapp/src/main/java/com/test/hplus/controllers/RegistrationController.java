package com.test.hplus.controllers;

import com.test.hplus.beans.User;
import com.test.hplus.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class RegistrationController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, "dateOfBirth",
                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }

    @PostMapping("/registeruser")
    public String registerUser(@Valid @ModelAttribute("newuser")User user, BindingResult result,
                               Model model) {
        LOGGER.debug("in registration controller");
        if(result.hasErrors()) {
            return "register";
        }
        userRepository.save(user);
        model.addAttribute("userRegisteredMsg", "User registered successfully");
        return "login";
    }
}
