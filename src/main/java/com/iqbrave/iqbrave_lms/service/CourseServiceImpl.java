package com.iqbrave.iqbrave_lms.service;

import com.iqbrave.iqbrave_lms.dto.CourseDTO;
import com.iqbrave.iqbrave_lms.entity.Course;
import com.iqbrave.iqbrave_lms.entity.User;
import com.iqbrave.iqbrave_lms.repository.CourseRepository;
import com.iqbrave.iqbrave_lms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Course createCourse(CourseDTO courseDTO) {
        Course course = new Course();
        course.setTitle(courseDTO.getTitle());
        course.setDescription(courseDTO.getDescription());

        if (courseDTO.getInstructor_id() != null) {
            User instructor = userRepository.findById(courseDTO.getInstructor_id())
                    .orElseThrow(() -> new RuntimeException("Instructor not found"));
            course.setInstructor(instructor);
        }

        return courseRepository.save(course);
    }

    @Override
    public Course updateCourse(Long id, Course course) {
        Course existing = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        existing.setTitle(course.getTitle());
        existing.setDescription(course.getDescription());
        return courseRepository.save(existing);
    }

    @Override
    public void deleteCourse(Long id) {
        Course existing = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        courseRepository.delete(existing); // hard delete
    }

    @Override
    public List<Course> listCourses() {
        return courseRepository.findAll(); // or findByActiveTrue for soft delete
    }

    @Override
    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }
}
