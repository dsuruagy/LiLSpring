package com.dsuruagy.boot.clr.roomclrapp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Room {
    private long id;
    private String name;
    private String number;
    private String info;

    public Room() {
        super();
    }

    public Room(long id, String name, String number, String info) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.info = info;
    }

    @Override
    public String toString() {
        return "Room [id=" + id + ", info=" + info + ", name=" + name + ", number=" + number + "]";
    }

}
