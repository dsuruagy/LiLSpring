package com.hanselnpetal.controller;

import com.hanselnpetal.domain.CustomerContact;
import com.hanselnpetal.service.ContactsManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class ContactsManagementController {

	@Autowired
	private ContactsManagementService contactsManagementService;

	@RequestMapping(value = "/addContact", method=RequestMethod.POST)
	public String processAddContactSubmit(@ModelAttribute CustomerContact aContact) {
		
		CustomerContact newContact = contactsManagementService.add(aContact);
		
		if (newContact != null) {
			return "/addContactForm";
		}
		
		return "redirect:/showAddContact";
	}

	@RequestMapping(value = "/contacts", method=RequestMethod.GET)
	public List<CustomerContact> getContacts() {

		return contactsManagementService.getContacts();
	}

	@RequestMapping(value= "/showAddContact", method=RequestMethod.GET)
	public String showAddContact() {

		// implement this

		return "/addContactForm";
	}
}
