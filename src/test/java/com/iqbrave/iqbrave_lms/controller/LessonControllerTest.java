package com.iqbrave.iqbrave_lms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iqbrave.iqbrave_lms.dto.LessonDTO;
import com.iqbrave.iqbrave_lms.entity.Lesson;
import com.iqbrave.iqbrave_lms.entity.CourseModule;
import com.iqbrave.iqbrave_lms.service.LessonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = LessonController.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = com.iqbrave.iqbrave_lms.security.JwtAuthenticationFilter.class)
)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class LessonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LessonService lessonService;

    @Autowired
    private ObjectMapper objectMapper;

    // ✅ Test Create Lesson
    @Test
    void testCreateLesson() throws Exception {
        LessonDTO dto = new LessonDTO();
        dto.setTitle("Intro Lesson");
        dto.setContent("Lesson Content");
        dto.setModuleId(1L);

        Lesson saved = new Lesson(1L, "Intro Lesson", "Lesson Content", new CourseModule(1L, "Module 1", "Description", null, null));

        when(lessonService.createLesson(any(LessonDTO.class))).thenReturn(saved);

        mockMvc.perform(post("/api/lessons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Intro Lesson"));

        verify(lessonService, times(1)).createLesson(any(LessonDTO.class));
    }

    // ✅ Test Update Lesson
    @Test
    void testUpdateLesson() throws Exception {
        LessonDTO dto = new LessonDTO();
        dto.setTitle("Updated Lesson");
        dto.setContent("Updated Content");
        dto.setModuleId(1L);

        Lesson updated = new Lesson(1L, "Updated Lesson", "Updated Content", new CourseModule(1L, "Module 1", "Description", null, null));

        when(lessonService.updateLesson(eq(1L), any(LessonDTO.class))).thenReturn(updated);

        mockMvc.perform(put("/api/lessons/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Lesson"));

        verify(lessonService, times(1)).updateLesson(eq(1L), any(LessonDTO.class));
    }

    // ✅ Test Delete Lesson
    @Test
    void testDeleteLesson() throws Exception {
        doNothing().when(lessonService).deleteLesson(1L);

        mockMvc.perform(delete("/api/lessons/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Lesson deleted successfully"));

        verify(lessonService, times(1)).deleteLesson(1L);
    }

    // ✅ Test List All Lessons
    @Test
    void testListLessons() throws Exception {
        Lesson l1 = new Lesson(1L, "Lesson 1", "Content 1", null);
        Lesson l2 = new Lesson(2L, "Lesson 2", "Content 2", null);

        when(lessonService.listLessons()).thenReturn(Arrays.asList(l1, l2));

        mockMvc.perform(get("/api/lessons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Lesson 1"))
                .andExpect(jsonPath("$[1].title").value("Lesson 2"));

        verify(lessonService, times(1)).listLessons();
    }

    // ✅ Test Get Lesson by ID
    @Test
    void testGetLessonById() throws Exception {
        Lesson lesson = new Lesson(1L, "Lesson 1", "Content 1", null);

        when(lessonService.getLessonById(1L)).thenReturn(Optional.of(lesson));

        mockMvc.perform(get("/api/lessons/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Lesson 1"));

        verify(lessonService, times(1)).getLessonById(1L);
    }

    // ✅ Test Get Lessons by Module
    @Test
    void testGetLessonsByModule() throws Exception {
        CourseModule courseModule = new CourseModule(1L, "Module 1", "Description", null, null);
        Lesson l1 = new Lesson(1L, "Lesson 1", "Content 1", courseModule);
        Lesson l2 = new Lesson(2L, "Lesson 2", "Content 2", courseModule);

        when(lessonService.listLessons()).thenReturn(Arrays.asList(l1, l2));

        mockMvc.perform(get("/api/lessons/module/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Lesson 1"))
                .andExpect(jsonPath("$[1].title").value("Lesson 2"));

        verify(lessonService, times(1)).listLessons();
    }
}
