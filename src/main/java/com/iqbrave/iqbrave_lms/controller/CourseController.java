package com.iqbrave.iqbrave_lms.controller;

import com.iqbrave.iqbrave_lms.dto.CourseDTO;
import com.iqbrave.iqbrave_lms.entity.Course;
import com.iqbrave.iqbrave_lms.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    // Create course
    @PostMapping
    public Course createCourse(@RequestBody CourseDTO courseDTO) {
        return courseService.createCourse(courseDTO);
    }

    // Update course
    @PutMapping("/{id}")
    public Course updateCourse(@PathVariable Long id, @RequestBody Course course) {
        return courseService.updateCourse(id, course);
    }

    // Delete course
    @DeleteMapping("/{id}")
    public String deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return "Course deleted successfully";
    }

    // List courses
    @GetMapping
    public List<Course> listCourses() {
        return courseService.listCourses();
    }

    // Get single course
    @GetMapping("/{id}")
    public Course getCourse(@PathVariable Long id) {
        return courseService.getCourseById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }
}
