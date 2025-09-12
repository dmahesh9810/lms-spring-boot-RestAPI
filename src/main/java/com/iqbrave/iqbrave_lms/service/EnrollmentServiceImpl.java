package com.iqbrave.iqbrave_lms.service;

import com.iqbrave.iqbrave_lms.dto.EnrollmentDTO;
import com.iqbrave.iqbrave_lms.entity.Course;
import com.iqbrave.iqbrave_lms.entity.Enrollment;
import com.iqbrave.iqbrave_lms.entity.User;
import com.iqbrave.iqbrave_lms.repository.CourseRepository;
import com.iqbrave.iqbrave_lms.repository.EnrollmentRepository;
import com.iqbrave.iqbrave_lms.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository,
                                 UserRepository userRepository,
                                 CourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public Enrollment enrollStudent(String studentEmail, Long courseId) {
        User student = userRepository.findByEmail(studentEmail)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        if (enrollmentRepository.existsByStudentIdAndCourseId(student.getId(), courseId)) {
            throw new RuntimeException("Student already enrolled in this course");
        }

        Enrollment enrollment = Enrollment.builder()
                .student(student)
                .course(course)
                .build();

        return enrollmentRepository.save(enrollment);
    }


    @Override
    public List<Enrollment> getEnrollmentsByStudent(Long studentId) {
        return enrollmentRepository.findByStudentId(studentId);
    }

    @Override
    public List<Enrollment> getEnrollmentsByCourse(Long courseId) {
        return enrollmentRepository.findByCourseId(courseId);
    }
    @Override
    public List<Enrollment> getEnrollmentsByStudentEmail(String studentEmail) {
        User student = userRepository.findByEmail(studentEmail)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        return enrollmentRepository.findByStudentId(student.getId());
    }
}
