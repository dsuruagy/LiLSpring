package com.example.university.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Staff {
    @Id
    private int id;
    private Person member;

    public Staff() {}


    public Staff(int id, Person member) {
        this.id = id;
        this.member = member;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Person getMember() {
        return member;
    }

    public void setMember(Person member) {
        this.member = member;
    }

    @Override
    public String toString() {
        return "Staff{" +
                "id=" + id +
                ", member=" + member +
                '}';
    }
}
