package com.iqbrave.iqbrave_lms.controller;

import com.iqbrave.iqbrave_lms.dto.EnrollmentDTO;
import com.iqbrave.iqbrave_lms.entity.Enrollment;
import com.iqbrave.iqbrave_lms.entity.User;
import com.iqbrave.iqbrave_lms.repository.UserRepository;
import com.iqbrave.iqbrave_lms.service.EnrollmentService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;
    private final UserRepository userRepository;

    public EnrollmentController(EnrollmentService enrollmentService,
                                UserRepository userRepository) {
        this.enrollmentService = enrollmentService;
        this.userRepository = userRepository;
    }

    // ✅ Student enrolls in course
    @PostMapping("/enroll/{courseId}")
    public Enrollment enrollStudent(@PathVariable Long courseId, Authentication authentication) {
        String email = authentication.getName(); // logged-in user’s email from JWT
        return enrollmentService.enrollStudent(email, courseId);
    }


    // ✅ Get enrollments by student
    @GetMapping("/student/me")
    public List<Enrollment> getMyEnrollments(Authentication authentication) {
        String email = authentication.getName();
        User student = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        return enrollmentService.getEnrollmentsByStudent(student.getId());
    }
    // Only for Admin/Instructor
    @GetMapping("/student/{studentId}")
    public List<Enrollment> getEnrollmentsByStudent(@PathVariable Long studentId) {
        return enrollmentService.getEnrollmentsByStudent(studentId);
    }


    // ✅ Get enrollments by course
    @GetMapping("/course/{courseId}")
    public List<Enrollment> getEnrollmentsByCourse(@PathVariable Long courseId) {
        return enrollmentService.getEnrollmentsByCourse(courseId);
    }
}
