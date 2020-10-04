package com.dsuruagy.boot.landon.roomwebapp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {
    private RoomService roomService;

    @Autowired
    public ApiController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/rooms")
    public List<Room> getAllRooms() {
        return this.roomService.getAllRooms(); 
    }
}
