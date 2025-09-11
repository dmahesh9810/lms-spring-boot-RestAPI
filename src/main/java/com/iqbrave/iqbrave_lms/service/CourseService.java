package com.iqbrave.iqbrave_lms.service;


import com.iqbrave.iqbrave_lms.dto.CourseDTO;
import com.iqbrave.iqbrave_lms.entity.Course;


import java.util.List;
import java.util.Optional;


public interface CourseService {


    Course createCourse(CourseDTO courseDTO);


    Course updateCourse(Long id, CourseDTO courseDTO);


    void deleteCourse(Long id);


    List<Course> listCourses();


    Optional<Course> getCourseById(Long id);
}