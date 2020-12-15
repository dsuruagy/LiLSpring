package com.example.university.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "course_id")
    private Integer id;

    @Column(name = "course_name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "course_dept_id")
    private Department department;

    @ManyToMany(mappedBy = "courses")
    private List<Student> students;

    @Column
    private int credits;

    @OneToOne
    private Staff instructor;

    @ManyToMany
    private List<Course> prerequisites = new ArrayList<>();

    public Course() {}

    public Course(String name, int credit, Staff instructor, Department department) {
        this.name = name;
        this.credits = credit;
        this.instructor = instructor;
        this.department = department;
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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public Staff getInstructor() {
        return instructor;
    }

    public void setInstructor(Staff instructor) {
        this.instructor = instructor;
    }

    public List<Course> getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(List<Course> prerequisites) {
        this.prerequisites = prerequisites;
    }

    @Override
    public String toString() {
        return "Course{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", credits=" + credits +
                ", instructor=" + instructor +
                ", department=" + department.getName() +
                '}';
    }

    public Course addPrerequisite(Course preRequisite) {
        this.prerequisites.add(preRequisite);

        return this;
    }
}
