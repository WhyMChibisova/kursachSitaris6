package com.example.kursach.models;

import jakarta.persistence.*;

@Entity
@Table(name = "user_course")
public class UsersCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "iduser_course")
    private int id;
    private int user_id;
    private int course_id;

    public UsersCourse() {
    }

    public UsersCourse(int user_id, int course_id) {
        this.user_id = user_id;
        this.course_id = course_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }
}
