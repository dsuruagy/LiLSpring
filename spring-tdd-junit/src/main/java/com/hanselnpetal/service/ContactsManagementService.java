package com.hanselnpetal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanselnpetal.data.repos.CustomerContactRepository;
import com.hanselnpetal.domain.CustomerContact;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactsManagementService {

	@Autowired
	private CustomerContactRepository customerContactRepository;
	
	public CustomerContact add(CustomerContact aContact) {
		
		CustomerContact newContact = null;

		if(aContact.getFirstName() != null) {
			newContact = customerContactRepository.save(aContact);
		}
		
		return newContact;	
	}

    public List<CustomerContact> getContacts() {
		List<CustomerContact> contacts = new ArrayList<>();

		customerContactRepository.findAll().forEach(contact -> contacts.add(contact));

		return contacts;
    }
	
	/*
	public CustomerContact addContactOccasion(CustomerContact aContact, ContactImportantOccasion anOccasion) {
		CustomerContact newContact = null;
		
		return newContact;	
	}
	*/
}
