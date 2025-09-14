package com.iqbrave.iqbrave_lms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iqbrave.iqbrave_lms.dto.EnrollmentDTO;
import com.iqbrave.iqbrave_lms.entity.Course;
import com.iqbrave.iqbrave_lms.entity.Enrollment;
import com.iqbrave.iqbrave_lms.entity.Role;
import com.iqbrave.iqbrave_lms.entity.User;
import com.iqbrave.iqbrave_lms.repository.UserRepository;
import com.iqbrave.iqbrave_lms.service.EnrollmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class EnrollmentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EnrollmentService enrollmentService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private EnrollmentController enrollmentController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        // Removed MockitoAnnotations.openMocks(this); to avoid duplicate/mock mismatch
        mockMvc = MockMvcBuilders.standaloneSetup(enrollmentController).build();
    }

    // ✅ Test Enroll Student
    @Test
    void testEnrollStudent() throws Exception {
        User student = new User();
        student.setId(1L);
        student.setEmail("student@test.com");
        student.setPassword("password");
        student.setRole(Role.STUDENT);

        Course course = new Course();
        course.setId(1L);
        course.setTitle("Spring Boot Basics");
        course.setDescription("Learn Spring Boot");

        Enrollment enrollment = new Enrollment();
        enrollment.setId(1L);
        enrollment.setStudent(student);
        enrollment.setCourse(course);

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("student@test.com");
        when(enrollmentService.enrollStudent(any(String.class), eq(1L))).thenReturn(enrollment);

        mockMvc.perform(post("/api/enrollments/enroll/1")
                        .principal(authentication)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.student.email").value("student@test.com"))
                .andExpect(jsonPath("$.course.title").value("Spring Boot Basics"));

        verify(enrollmentService, times(1)).enrollStudent(any(String.class), eq(1L));
    }

    // ✅ Test Get My Enrollments
    @Test
    void testGetMyEnrollments() throws Exception {
        User student = new User();
        student.setId(1L);
        student.setEmail("student@test.com");
        student.setPassword("password");
        student.setRole(Role.STUDENT);

        Enrollment e1 = new Enrollment();
        e1.setId(1L);
        e1.setStudent(student);
        Course c1 = new Course();
        c1.setId(1L);
        c1.setTitle("Spring Boot Basics");
        c1.setDescription("Learn Spring Boot");
        e1.setCourse(c1);

        Enrollment e2 = new Enrollment();
        e2.setId(2L);
        e2.setStudent(student);
        Course c2 = new Course();
        c2.setId(2L);
        c2.setTitle("Java Advanced");
        c2.setDescription("Deep dive Java");
        e2.setCourse(c2);

        when(userRepository.findByEmail("student@test.com")).thenReturn(Optional.of(student));
        when(enrollmentService.getEnrollmentsByStudent(1L)).thenReturn(Arrays.asList(e1, e2));

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("student@test.com");

        mockMvc.perform(get("/api/enrollments/student/me")
                        .principal(authentication)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].course.title").value("Spring Boot Basics"))
                .andExpect(jsonPath("$[1].course.title").value("Java Advanced"));

        verify(enrollmentService, times(1)).getEnrollmentsByStudent(1L);
    }

    // ✅ Test Get Enrollments by Student ID (Admin/Instructor)
    @Test
    void testGetEnrollmentsByStudentId() throws Exception {
        User student = new User();
        student.setId(1L);
        student.setEmail("student@test.com");
        student.setPassword("password");
        student.setRole(Role.STUDENT);

        Enrollment e1 = new Enrollment();
        e1.setId(1L);
        e1.setStudent(student);
        Course course = new Course();
        course.setId(1L);
        course.setTitle("Spring Boot Basics");
        course.setDescription("Learn Spring Boot");
        e1.setCourse(course);

        when(enrollmentService.getEnrollmentsByStudent(1L)).thenReturn(List.of(e1));

        mockMvc.perform(get("/api/enrollments/student/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].student.email").value("student@test.com"))
                .andExpect(jsonPath("$[0].course.title").value("Spring Boot Basics"));

        verify(enrollmentService, times(1)).getEnrollmentsByStudent(1L);
    }

    // ✅ Test Get Enrollments by Course ID (Admin/Instructor)
    @Test
    void testGetEnrollmentsByCourseId() throws Exception {
        User student1 = new User();
        student1.setId(1L);
        student1.setEmail("student1@test.com");
        student1.setPassword("password");
        student1.setRole(Role.STUDENT);

        User student2 = new User();
        student2.setId(2L);
        student2.setEmail("student2@test.com");
        student2.setPassword("password");
        student2.setRole(Role.STUDENT);

        Course course = new Course();
        course.setId(1L);
        course.setTitle("Spring Boot Basics");
        course.setDescription("Learn Spring Boot");

        Enrollment e1 = new Enrollment();
        e1.setId(1L);
        e1.setStudent(student1);
        e1.setCourse(course);

        Enrollment e2 = new Enrollment();
        e2.setId(2L);
        e2.setStudent(student2);
        e2.setCourse(course);

        // Use anyLong() to be robust against argument boxing/matching
        when(enrollmentService.getEnrollmentsByCourse(org.mockito.ArgumentMatchers.anyLong()))
                .thenReturn(Arrays.asList(e1, e2));

        mockMvc.perform(get("/api/enrollments/course/1"))
                .andDo(org.springframework.test.web.servlet.result.MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].student.email").value("student1@test.com"))
                .andExpect(jsonPath("$[1].student.email").value("student2@test.com"));

        verify(enrollmentService, times(1)).getEnrollmentsByCourse(1L);
    }
    // =========================
    // VALIDATION TESTS
    // =========================

    @Test
    void testCreateEnrollment_MissingStudentId() throws Exception {
        EnrollmentDTO dto = EnrollmentDTO.builder()
                .studentId(null) // invalid
                .courseId(1L)
                .build();

        mockMvc.perform(post("/api/enrollments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.studentId").value("Student ID is required"));

        verify(enrollmentService, never()).enrollStudent(any(), any());
    }

    @Test
    void testCreateEnrollment_MissingCourseId() throws Exception {
        EnrollmentDTO dto = EnrollmentDTO.builder()
                .studentId(1L)
                .courseId(null) // invalid
                .build();

        mockMvc.perform(post("/api/enrollments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.courseId").value("Course ID is required"));

        verify(enrollmentService, never()).enrollStudent(any(), any());
    }

    @Test
    void testCreateEnrollment_MissingBothIds() throws Exception {
        EnrollmentDTO dto = EnrollmentDTO.builder()
                .studentId(null)
                .courseId(null)
                .build();

        mockMvc.perform(post("/api/enrollments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.studentId").value("Student ID is required"))
                .andExpect(jsonPath("$.errors.courseId").value("Course ID is required"));

        verify(enrollmentService, never()).enrollStudent(any(), any());
    }
}
