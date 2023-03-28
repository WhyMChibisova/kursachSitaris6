package com.example.kursach.repositories;

import com.example.kursach.models.UsersCourse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersCourseRepository extends JpaRepository<UsersCourse, Integer> {
}
