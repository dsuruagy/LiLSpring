package com.test.hplus.controllers;

import com.test.hplus.beans.Login;
import com.test.hplus.beans.User;
import com.test.hplus.exceptions.ApplicationException;
import com.test.hplus.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserRepository userRepository;

    @PostMapping("/login")
    public String login(@ModelAttribute("login") Login login) {
        User user = userRepository.searchByName(login.getUsername());
        if(user != null &&
                user.getPassword().equals(login.getPassword())) {
            return "search";
        } else {
            throw new ApplicationException("User/Password not found");
        }
    }
}
