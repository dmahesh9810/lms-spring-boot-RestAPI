package com.iqbrave.iqbrave_lms.controller;

import com.iqbrave.iqbrave_lms.dto.QuestionDTO;
import com.iqbrave.iqbrave_lms.service.QuestionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/quiz/{quizId}")
    public QuestionDTO addQuestion(@PathVariable Long quizId, @RequestBody QuestionDTO questionDTO) {
        // ... existing code ...
        return questionService.addQuestion(quizId, questionDTO);
    }

    @PutMapping("/{id}")
    public QuestionDTO updateQuestion(@PathVariable Long id, @RequestBody QuestionDTO questionDTO) {
        // ... existing code ...
        return questionService.updateQuestion(id, questionDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteQuestion(@PathVariable Long id) {
        // ... existing code ...
        questionService.deleteQuestion(id);
    }

    @GetMapping("/quiz/{quizId}")
    public List<QuestionDTO> getQuestionsByQuiz(@PathVariable Long quizId) {
        // ... existing code ...
        return questionService.getQuestionsByQuiz(quizId);
    }
}
