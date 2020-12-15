package com.example.university.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Department {
    @Id
    private int id;
    private String name;
    @DBRef(db = "chair")
    private Staff chair;

    public Department() { }

    public Department(int id, String name, Staff chair) {
        this.id = id;
        this.name = name;
        this.chair = chair;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Staff getChair() {
        return chair;
    }

    public void setChair(Staff chair) {
        this.chair = chair;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", chair=" + chair +
                '}';
    }
}
