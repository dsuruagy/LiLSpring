package com.dsuruagy.lil.learningspring.business.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dsuruagy.lil.learningspring.business.domain.RoomReservation;
import com.dsuruagy.lil.learningspring.data.entity.Guest;
import com.dsuruagy.lil.learningspring.data.entity.Reservation;
import com.dsuruagy.lil.learningspring.data.entity.Room;
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
        Iterable<Room> rooms = roomRepository.findAll();
        Map<Long, RoomReservation> roomReservationMap = new HashMap<>();
        rooms.forEach(room -> {
            RoomReservation roomReservation = new RoomReservation();
            roomReservation.setRoomId(room.getId());
            roomReservation.setRoomName(room.getName());
            roomReservation.setRoomNumber(room.getNumber());
            roomReservationMap.put(room.getId(), roomReservation);
        });

        Iterable<Reservation> reservations = 
            reservationRepository.findReservationByReservationDate(new java.sql.Date(date.toEpochDay()));
        reservations.forEach(reservation -> {
            RoomReservation roomReservation = roomReservationMap.get(reservation.getRoomId());
            roomReservation.setDate(date);

            Guest guest = guestRepository.findById(reservation.getGuestId()).orElse(null);
            roomReservation.setGuestId(guest.getId());
            roomReservation.setFirstName(guest.getFirstName());
            roomReservation.setLastName(guest.getLastName());
        });

        List<RoomReservation> roomReservations = new ArrayList<>();
        for(Long id: roomReservationMap.keySet()) {
            roomReservations.add(roomReservationMap.get(id));
        }
        return roomReservations;
    }
}
