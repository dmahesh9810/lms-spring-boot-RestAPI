package com.iqbrave.iqbrave_lms.controller;

import com.iqbrave.iqbrave_lms.dto.QuizAttemptDTO;
import com.iqbrave.iqbrave_lms.entity.QuizAttempt;
import com.iqbrave.iqbrave_lms.service.QuizAttemptService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attempts")
public class QuizAttemptController {

    private final QuizAttemptService attemptService;

    public QuizAttemptController(QuizAttemptService attemptService) {
        this.attemptService = attemptService;
    }

    @PostMapping("/start/{quizId}/student/{studentId}")
    public QuizAttempt startAttempt(@PathVariable Long quizId, @PathVariable Long studentId) {
        return attemptService.startAttempt(quizId, studentId);
    }

    @PostMapping("/submit/{attemptId}")
    public QuizAttempt submitAttempt(@PathVariable Long attemptId, @RequestBody QuizAttemptDTO attemptDTO) {
        return attemptService.submitAttempt(attemptId, attemptDTO);
    }

    @GetMapping("/student/{studentId}")
    public List<QuizAttempt> getAttemptsByStudent(@PathVariable Long studentId) {
        return attemptService.getAttemptsByStudent(studentId);
    }

    @GetMapping("/quiz/{quizId}")
    public List<QuizAttempt> getAttemptsByQuiz(@PathVariable Long quizId) {
        return attemptService.getAttemptsByQuiz(quizId);
    }
}
