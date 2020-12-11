package com.example.university.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "DEPARTMENT")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "dept_id")
    private Integer id;

    @Column(name = "dept_name")
    private String name;

    @OneToMany(mappedBy = "department", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Course> courses =  new ArrayList<>();

    @OneToOne
    private Staff chair;

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public Staff getChair() {
        return chair;
    }

    public void setChair(Staff chair) {
        this.chair = chair;
    }

    public Department() {
    }

    public Department(String name) {
        this.name = name;
    }

    public Department(String name, Staff chair) {
        this.name = name;
        this.chair = chair;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Department{" + "id=" + id + ", name='" + name + '\'' +
                ", courses=" + courses + '}';
    }
}
