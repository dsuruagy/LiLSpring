package com.dsuruagy.lil.learningspring.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "GUEST")
public class Guest {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name = "GUEST_ID")
    private long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String emailAddress;

    @Column
    private String address;

    @Column
    private String state;

    @Column
    private String phoneNumber;
}
