package com.dsuruagy.lil.learningspring.business.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.dsuruagy.lil.learningspring.data.entity.Guest;
import com.dsuruagy.lil.learningspring.data.repository.GuestRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GuestService {
    private final GuestRepository guestRepository;

    @Autowired
    public GuestService(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    public List<Guest> getGuests() {

        Iterable<Guest> guestIterable = this.guestRepository.findAll();
        
        List<Guest> guestList = StreamSupport
            .stream(guestIterable.spliterator(), false)
            .collect(Collectors.toList());

        guestList.sort(new Comparator<Guest>(){

			@Override
			public int compare(Guest o1, Guest o2) {
				if (o1.getLastName() == o2.getLastName()) {
                    return o1.getFirstName().compareTo(o2.getFirstName());
                }
                return o1.getLastName().compareTo(o2.getLastName());
			}
 
        });

        return guestList;
    }
}
