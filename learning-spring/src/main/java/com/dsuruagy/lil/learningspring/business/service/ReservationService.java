package com.dsuruagy.lil.learningspring.business.service;

import java.time.LocalDate;
import java.util.List;

import com.dsuruagy.lil.learningspring.business.domain.RoomReservation;
import com.dsuruagy.lil.learningspring.data.repository.GuestRepository;
import com.dsuruagy.lil.learningspring.data.repository.ReservationRepository;
import com.dsuruagy.lil.learningspring.data.repository.RoomRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;
    private final ReservationRepository reservationRepository;
    
    @Autowired
    public ReservationService(RoomRepository roomRepository, GuestRepository guestRepository,
            ReservationRepository reservationRepository) {
        this.roomRepository = roomRepository;
        this.guestRepository = guestRepository;
        this.reservationRepository = reservationRepository;
    }
    
    public List<RoomReservation> getReservationsForDate (LocalDate date) {
        return null;
    }
}
