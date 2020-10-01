package com.dsuruagy.lil.learningspring.web;

import com.dsuruagy.lil.learningspring.business.service.GuestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/guests")
public class GuestWebController {
    private final GuestService guestService;

	@Autowired
    public GuestWebController(GuestService guestService) {
		this.guestService = guestService;
	}

    @GetMapping
    public String getGuests(Model model) {
        model.addAttribute("guests", this.guestService.getGuests());
        
        return "guests";
    }
}
