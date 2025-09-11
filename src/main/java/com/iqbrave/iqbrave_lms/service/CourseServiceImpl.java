package com.iqbrave.iqbrave_lms.service;


import com.iqbrave.iqbrave_lms.dto.CourseDTO;
import com.iqbrave.iqbrave_lms.entity.Course;
import com.iqbrave.iqbrave_lms.entity.User;
import com.iqbrave.iqbrave_lms.repository.CourseRepository;
import com.iqbrave.iqbrave_lms.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {


    private final CourseRepository courseRepository;
    private final UserRepository userRepository;


    public CourseServiceImpl(CourseRepository courseRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }


    @Override
    public Course createCourse(CourseDTO dto) {
        Course course = new Course();
        course.setTitle(dto.getTitle());
        course.setDescription(dto.getDescription());
        course.setActive(dto.isActive());

        if (dto.getInstructor_id() != null) {
            User instructor = userRepository.findById(dto.getInstructor_id())
                    .orElseThrow(() -> new RuntimeException("Instructor not found with id " + dto.getInstructor_id()));
            course.setInstructor(instructor);
        }

        return courseRepository.save(course);
    }



    @Override
    public Course updateCourse(Long id, CourseDTO dto) {
        Course existing = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id " + id));


        if (dto.getTitle() != null) existing.setTitle(dto.getTitle());
        if (dto.getDescription() != null) existing.setDescription(dto.getDescription());
        existing.setActive(dto.isActive());


        if (dto.getInstructor_id() != null) {  // use the exact field name
            User instructor = userRepository.findById(dto.getInstructor_id())
                    .orElseThrow(() -> new RuntimeException(
                            "Instructor not found with id " + dto.getInstructor_id()));
            existing.setInstructor(instructor);
        }



        return courseRepository.save(existing);
    }
    @Override
    @Transactional
    public void deleteCourse(Long id) {
        Course existing = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id " + id));
        courseRepository.delete(existing);
    }


    @Override
    public List<Course> listCourses() {
        return courseRepository.findAll();
    }


    @Override
    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }
}