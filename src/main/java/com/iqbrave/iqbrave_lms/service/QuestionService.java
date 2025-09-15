package com.iqbrave.iqbrave_lms.service;

import com.iqbrave.iqbrave_lms.dto.QuestionDTO;
import com.iqbrave.iqbrave_lms.entity.Question;
import java.util.List;

public interface QuestionService {
    QuestionDTO addQuestion(Long quizId, QuestionDTO questionDTO);
    QuestionDTO updateQuestion(Long id, QuestionDTO questionDTO);
    void deleteQuestion(Long id);
    List<QuestionDTO> getQuestionsByQuiz(Long quizId);
}
