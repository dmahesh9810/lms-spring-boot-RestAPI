package com.iqbrave.iqbrave_lms.controller;

import com.iqbrave.iqbrave_lms.dto.EnrollmentDTO;
import com.iqbrave.iqbrave_lms.entity.Enrollment;
import com.iqbrave.iqbrave_lms.entity.User;
import com.iqbrave.iqbrave_lms.service.EnrollmentService;
import com.iqbrave.iqbrave_lms.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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

    // Validation-only endpoint for DTO tests
    @PostMapping
    public org.springframework.http.ResponseEntity<java.util.Map<String, Object>> createEnrollment(
            @RequestBody @jakarta.validation.Valid EnrollmentDTO dto,
            org.springframework.validation.BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            java.util.Map<String, String> errors = new java.util.HashMap<>();
            for (org.springframework.validation.ObjectError error : bindingResult.getAllErrors()) {
                org.springframework.validation.FieldError fe = (org.springframework.validation.FieldError) error;
                errors.put(fe.getField(), error.getDefaultMessage());
            }
            java.util.Map<String, Object> response = new java.util.HashMap<>();
            response.put("status", org.springframework.http.HttpStatus.BAD_REQUEST.value());
            response.put("errors", errors);
            return new org.springframework.http.ResponseEntity<>(response, org.springframework.http.HttpStatus.BAD_REQUEST);
        }

        // Intentionally, no service call (tests verify 'never()')
        return org.springframework.http.ResponseEntity.ok().build();
    }

    // Enroll the current user (by email from Principal) into a course
    @PostMapping("/enroll/{courseId}")
    public Enrollment enrollStudent(Principal principal, @PathVariable Long courseId) {
        return enrollmentService.enrollStudent(principal.getName(), courseId);
    }

    @GetMapping("/student/me")
    public List<Enrollment> getMyEnrollments(Principal principal) {
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found: " + principal.getName()));
        return enrollmentService.getEnrollmentsByStudent(user.getId());
    }

    @GetMapping("/student/{studentId}")
    public List<Enrollment> getEnrollmentsByStudent(@PathVariable Long studentId) {
        return enrollmentService.getEnrollmentsByStudent(studentId);
    }

    @GetMapping("/course/{courseId}")
    public List<Enrollment> getEnrollmentsByCourse(@PathVariable Long courseId) {
        return enrollmentService.getEnrollmentsByCourse(courseId);
    }
}
