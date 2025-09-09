package com.iqbrave.iqbrave_lms.repository;

import com.iqbrave.iqbrave_lms.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByActiveTrue(); // List only active courses
}

