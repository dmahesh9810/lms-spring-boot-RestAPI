package com.iqbrave.iqbrave_lms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iqbrave.iqbrave_lms.dto.CourseDTO;
import com.iqbrave.iqbrave_lms.entity.Course;
import com.iqbrave.iqbrave_lms.service.CourseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = CourseController.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = com.iqbrave.iqbrave_lms.security.JwtAuthenticationFilter.class)
)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CourseService courseService;

    @Autowired
    private ObjectMapper objectMapper;

    // ✅ Test Create Course
    @Test
    void testCreateCourse() throws Exception {
        CourseDTO dto = new CourseDTO("Java Basics", "Intro to Java");
        Course saved = new Course(1L, "Java Basics", "Intro to Java");

        when(courseService.createCourse(any(CourseDTO.class))).thenReturn(saved);

        mockMvc.perform(post("/api/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Java Basics"));

        verify(courseService, times(1)).createCourse(any(CourseDTO.class));
    }

    // ✅ Test Update Course
    @Test
    void testUpdateCourse() throws Exception {
        CourseDTO dto = new CourseDTO("Java Advanced", "Deep dive into Java");
        Course updated = new Course(1L, "Java Advanced", "Deep dive into Java");

        when(courseService.updateCourse(eq(1L), any(CourseDTO.class))).thenReturn(updated);

        mockMvc.perform(put("/api/courses/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Java Advanced"));

        verify(courseService, times(1)).updateCourse(eq(1L), any(CourseDTO.class));
    }

    // ✅ Test Delete Course
    @Test
    void testDeleteCourse() throws Exception {
        doNothing().when(courseService).deleteCourse(1L);

        mockMvc.perform(delete("/api/courses/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Course deleted successfully"));

        verify(courseService, times(1)).deleteCourse(1L);
    }

    // ✅ Test List All Courses
    @Test
    void testListCourses() throws Exception {
        when(courseService.listCourses()).thenReturn(Arrays.asList(
                new Course(1L, "Java Basics", "Intro"),
                new Course(2L, "Spring Boot", "Backend framework")
        ));

        mockMvc.perform(get("/api/courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Java Basics"))
                .andExpect(jsonPath("$[1].title").value("Spring Boot"));

        verify(courseService, times(1)).listCourses();
    }

    // ✅ Test Get Course by ID
    @Test
    void testGetCourseById() throws Exception {
        Course course = new Course(1L, "Spring Boot", "Backend framework");

        when(courseService.getCourseById(1L)).thenReturn(Optional.of(course));

        mockMvc.perform(get("/api/courses/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Spring Boot"));

        verify(courseService, times(1)).getCourseById(1L);
    }
}
