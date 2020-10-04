package com.dsuruagy.boot.landon.roomwebapp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class RoomService {
    private static List<Room> rooms = new ArrayList<>();

    static {
        for(int i=0; i<10; i++) {
            rooms.add(new Room(i, "Room " + i, "R"+i, "Q"));
        }
    }
    
    public List<Room> getAllRooms() {
        return this.rooms;
    }
}
