package com.example.university.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Person {
    @Column(name = "student_first_name")
    private String firstName;

    @Column(name = "student_last_name")
    private String lastName;

    public Person() {}

    public Person(String first_name, String last_name) {
        this.firstName = first_name;
        this.lastName = last_name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
