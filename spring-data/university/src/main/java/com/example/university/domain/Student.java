package com.example.university.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "STUDENT")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "student_id", nullable = false)
    private Integer id;

    @Column(name = "student_fulltime", nullable = false)
    private boolean fullTime;

    @Column(name = "student_age", nullable = false)
    private Integer age;

    @Embedded
    private Person attendee;

    public Student() {
    }

    @ManyToMany()
    @JoinTable(name = "Enrollment", joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id"))
    private List<Course> courses = new ArrayList<>();

    public Student(Person person, boolean fullTime, int age) {
        this.attendee = person;
        this.fullTime = fullTime;
        this.age = age;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isFullTime() {
        return fullTime;
    }

    public void setFullTime(boolean fullTime) {
        this.fullTime = fullTime;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Person getAttendee() {
        return attendee;
    }

    public void setAttendee(Person attendee) {
        this.attendee = attendee;
    }

    @Override
    public String toString() {
        return "Student{" + "studentId=" + id + ", " +
                attendee + ", fullTime=" + fullTime +
                ", age=" + age + "}\n";
    }
}
