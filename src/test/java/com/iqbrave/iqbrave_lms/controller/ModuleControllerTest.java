package com.iqbrave.iqbrave_lms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iqbrave.iqbrave_lms.dto.ModuleDTO;
import com.iqbrave.iqbrave_lms.entity.Course;
import com.iqbrave.iqbrave_lms.entity.CourseModule;
import com.iqbrave.iqbrave_lms.service.ModuleService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.iqbrave.iqbrave_lms.security.JwtAuthenticationFilter;
import com.iqbrave.iqbrave_lms.security.JwtUtil;
import com.iqbrave.iqbrave_lms.service.CustomUserDetailsService;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = ModuleController.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthenticationFilter.class)
)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class ModuleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JwtUtil jwtUtil;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    // Replace the @TestConfiguration bean with a per-test mock:
    @MockitoBean
    private ModuleService moduleService;

    @Autowired
    private ObjectMapper objectMapper;

    // ✅ CREATE
    @Test
    void testCreateModule() throws Exception {
        ModuleDTO dto = new ModuleDTO(null, "Java Basics", "Intro to Java", 1L, null);
        CourseModule courseModule = new CourseModule(1L, "Java Basics", "Intro to Java", new Course(), null);

        Mockito.when(moduleService.createModule(any(ModuleDTO.class))).thenReturn(courseModule);

        mockMvc.perform(post("/api/modules")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Java Basics"));
    }

    // ✅ UPDATE
    @Test
    void testUpdateModule() throws Exception {
        ModuleDTO dto = new ModuleDTO(null, "Spring Boot", "Updated", 1L, null);
        CourseModule courseModule = new CourseModule(1L, "Spring Boot", "Updated", new Course(), null);

        Mockito.when(moduleService.updateModule(eq(1L), any(ModuleDTO.class))).thenReturn(courseModule);

        mockMvc.perform(put("/api/modules/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Spring Boot"));
    }

    // ✅ DELETE
    @Test
    void testDeleteModule() throws Exception {
        mockMvc.perform(delete("/api/modules/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Module deleted successfully"));
    }

    // ✅ LIST
    @Test
    void testListModules() throws Exception {
        CourseModule m1 = new CourseModule(1L, "Java", "Basics", new Course(), null);
        CourseModule m2 = new CourseModule(2L, "Spring", "Boot", new Course(), null);

        Mockito.when(moduleService.listModules()).thenReturn(List.of(m1, m2));

        mockMvc.perform(get("/api/modules"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    // ✅ GET BY ID
    @Test
    void testGetModuleById() throws Exception {
        CourseModule courseModule = new CourseModule(1L, "Java", "Basics", new Course(), null);

        Mockito.when(moduleService.getModuleById(1L)).thenReturn(Optional.of(courseModule));

        mockMvc.perform(get("/api/modules/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Java"));
    }

    // ===============================
    // ✅ VALIDATION TESTS
    // ===============================

    @Test
    void testCreateModule_MissingTitle() throws Exception {
        ModuleDTO dto = new ModuleDTO(null, "", "Intro to Java", 1L, null);

        mockMvc.perform(post("/api/modules")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.title").value("Title is required"));

        Mockito.verify(moduleService, Mockito.never()).createModule(any());
    }

    @Test
    void testCreateModule_MissingDescription() throws Exception {
        ModuleDTO dto = new ModuleDTO(null, "Java Basics", "", 1L, null);

        mockMvc.perform(post("/api/modules")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.description").value("Description is required"));

        Mockito.verify(moduleService, Mockito.never()).createModule(any());
    }

    @Test
    void testCreateModule_MissingCourseId() throws Exception {
        ModuleDTO dto = new ModuleDTO(null, "Java Basics", "Intro to Java", null, null);

        mockMvc.perform(post("/api/modules")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.courseId").value("Course ID is required"));

        Mockito.verify(moduleService, Mockito.never()).createModule(any());
    }
}
