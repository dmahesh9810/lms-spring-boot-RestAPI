package com.iqbrave.iqbrave_lms.service;

import com.iqbrave.iqbrave_lms.dto.EnrollmentDTO;
import com.iqbrave.iqbrave_lms.entity.Enrollment;

import java.util.List;

public interface EnrollmentService {
    Enrollment enrollStudent(String studentEmail, Long courseId);
    List<Enrollment> getEnrollmentsByStudent(Long studentId);
    List<Enrollment> getEnrollmentsByCourse(Long courseId);
    List<Enrollment> getEnrollmentsByStudentEmail(String studentEmail);
}

