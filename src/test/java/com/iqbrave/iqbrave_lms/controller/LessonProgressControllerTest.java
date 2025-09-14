package com.iqbrave.iqbrave_lms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iqbrave.iqbrave_lms.dto.LessonProgressDTO;
import com.iqbrave.iqbrave_lms.entity.Role;
import com.iqbrave.iqbrave_lms.entity.User;
import com.iqbrave.iqbrave_lms.repository.UserRepository;
import com.iqbrave.iqbrave_lms.service.LessonProgressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class LessonProgressControllerTest {

    private MockMvc mockMvc;

    @Mock
    private LessonProgressService lessonProgressService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LessonProgressController lessonProgressController;

    private ObjectMapper objectMapper = new ObjectMapper();

    private LessonProgressDTO sampleProgress;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(lessonProgressController).build();

        sampleProgress = new LessonProgressDTO(1L, 1L, 1L, true);
    }

    @Test
    void testMarkCompleted() throws Exception {
        when(lessonProgressService.markLessonCompleted(any(LessonProgressDTO.class)))
                .thenReturn(sampleProgress);
        // Provide principal and user repository stub
        User current = new User();
        current.setId(1L);
        current.setEmail("student3@example.com");
        current.setRole(Role.STUDENT);
        when(userRepository.findByEmail("student3@example.com"))
                .thenReturn(Optional.of(current));

        mockMvc.perform(post("/api/progress/complete")
                        .principal(() -> "student3@example.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleProgress)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.studentId").value(1))
                .andExpect(jsonPath("$.lessonId").value(1))
                .andExpect(jsonPath("$.completed").value(true));
    }

    @Test
    void testGetProgressByStudent() throws Exception {
        List<LessonProgressDTO> progressList = Arrays.asList(
                new LessonProgressDTO(1L, 1L, 1L, true),
                new LessonProgressDTO(2L, 1L, 2L, false)
        );

        when(lessonProgressService.getProgressByStudent(1L)).thenReturn(progressList);

        mockMvc.perform(get("/api/progress/student/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].completed").value(true))
                .andExpect(jsonPath("$[1].completed").value(false));
    }




    @Test
    void testGetCompletionPercentage() throws Exception {
        // The controller checks current user via principal -> userRepository
        User current = new User();
        current.setId(3L);
        current.setEmail("student3@example.com");
        current.setRole(Role.STUDENT);
        when(userRepository.findByEmail("student3@example.com"))
                .thenReturn(Optional.of(current));
        when(lessonProgressService.getCompletionPercentage(3L, 2L)).thenReturn(75.0);

        mockMvc.perform(get("/api/progress/student/3/course/2/percentage")
                        .principal(() -> "student3@example.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("75.0"))
                .andDo(print());
    }
}
