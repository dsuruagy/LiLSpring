package com.example.university.domain;

import javax.persistence.*;

@Entity
@Table(name="Staff_member")
public class Staff {
    @Id
    @GeneratedValue
    private Integer id;

    @Embedded
    private Person member;

    public Staff() {}

    public Staff(Person person) {
        this.member = person;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
