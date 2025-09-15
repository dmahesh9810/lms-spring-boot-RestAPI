package com.iqbrave.iqbrave_lms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iqbrave.iqbrave_lms.dto.QuestionDTO;
import com.iqbrave.iqbrave_lms.dto.QuizAttemptDTO;
import com.iqbrave.iqbrave_lms.dto.QuizDTO;
import com.iqbrave.iqbrave_lms.dto.StudentAnswerDTO;
import com.iqbrave.iqbrave_lms.entity.Role;
import com.iqbrave.iqbrave_lms.entity.User;
import com.iqbrave.iqbrave_lms.service.QuestionService;
import com.iqbrave.iqbrave_lms.service.QuizAttemptService;
import com.iqbrave.iqbrave_lms.service.QuizService;
import com.iqbrave.iqbrave_lms.repository.UserRepository;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(MockitoExtension.class)
public class QuizControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private QuizService quizService;

    @Mock
    private QuestionService questionService;

    @Mock
    private QuizAttemptService quizAttemptService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private QuizController quizController;

    private QuizDTO sampleQuiz;
    private QuestionDTO sampleQuestion;
    private QuizAttemptDTO sampleAttempt;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(quizController).build();

        sampleQuiz = new QuizDTO();
        sampleQuiz.setId(1L);
        sampleQuiz.setTitle("Java Basics Quiz");
        sampleQuiz.setDescription("Test Java fundamentals");
        sampleQuiz.setCourseId(1L);

        sampleQuestion = new QuestionDTO();
        sampleQuestion.setId(1L);
        sampleQuestion.setQuestionText("What is JVM?");
        sampleQuestion.setType("MCQ");
        sampleQuestion.setOptions(Arrays.asList("Java Virtual Machine","Option2","Option3"));
        sampleQuestion.setCorrectAnswer("Java Virtual Machine");
        sampleQuestion.setMarks(5L);

        sampleAttempt = new QuizAttemptDTO();
        sampleAttempt.setId(1L);
        sampleAttempt.setStudentId(1L);
        sampleAttempt.setQuizId(1L);
        sampleAttempt.setAnswers(Arrays.asList(
                new StudentAnswerDTO(1L, "Java Virtual Machine", true),
                new StudentAnswerDTO(2L, "Option2", false)
        ));
    }

    // ==================== Quiz Tests ====================

    @Test
    void testCreateQuiz() throws Exception {
        when(quizService.createQuiz(any(QuizDTO.class))).thenReturn(sampleQuiz);

        mockMvc.perform(post("/api/quizzes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleQuiz)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Java Basics Quiz"))
                .andDo(print());
    }

    @Test
    void testGetQuizById() throws Exception {
        when(quizService.getQuizById(1L)).thenReturn(sampleQuiz);

        mockMvc.perform(get("/api/quizzes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Java Basics Quiz"))
                .andDo(print());
    }

    // ==================== Question Tests ====================

    @Test
    void testAddQuestion() throws Exception {
        when(questionService.addQuestion(any(Long.class), any(QuestionDTO.class)))
                .thenReturn(sampleQuestion);

        mockMvc.perform(post("/api/questions/quiz/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleQuestion)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.questionText").value("What is JVM?"))
                .andDo(print());
    }

    @Test
    void testGetQuestionsByQuiz() throws Exception {
        when(questionService.getQuestionsByQuiz(1L))
                .thenReturn(Arrays.asList(sampleQuestion));

        mockMvc.perform(get("/api/questions/quiz/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].questionText").value("What is JVM?"))
                .andDo(print());
    }

    // ==================== Quiz Attempt Tests ====================

    @Test
    void testStartQuizAttempt() throws Exception {
        when(quizAttemptService.startAttempt(1L, 1L)).thenReturn(null); // return can be adjusted

        mockMvc.perform(post("/api/attempts/start/1/student/1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void testSubmitQuizAttempt() throws Exception {
        when(quizAttemptService.submitAttempt(any(Long.class), any(QuizAttemptDTO.class)))
                .thenReturn(null); // return can be adjusted

        mockMvc.perform(post("/api/attempts/submit/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleAttempt)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void testGetAttemptsByStudent() throws Exception {
        when(quizAttemptService.getAttemptsByStudent(1L))
                .thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/attempts/student/1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void testGetAttemptsByQuiz() throws Exception {
        when(quizAttemptService.getAttemptsByQuiz(1L))
                .thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/attempts/quiz/1"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
