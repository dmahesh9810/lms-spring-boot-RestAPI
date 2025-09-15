package com.iqbrave.iqbrave_lms.service;

import com.iqbrave.iqbrave_lms.dto.*;
import java.util.List;

public interface QuizService {

    // Quiz Management
    QuizDTO createQuiz(QuizDTO quizDTO);
    QuizDTO updateQuiz(Long id, QuizDTO quizDTO);
    void deleteQuiz(Long id);
    QuizDTO getQuiz(Long id);
    List<QuizDTO> getQuizzesByCourse(Long courseId);

    // Backwards-compatibility alias used in tests
    default QuizDTO getQuizById(Long id) {
        // ... existing code ...
        return getQuiz(id);
    }

    // Questions
    QuestionDTO addQuestion(Long quizId, QuestionDTO dto);
    void deleteQuestion(Long questionId);

    // Attempts
    QuizAttemptDTO attemptQuiz(Long quizId, QuizAttemptRequestDTO attemptRequest);
    QuizAttemptDTO getAttemptResult(Long quizId, Long studentId);
    List<QuizAttemptDTO> listAttemptsForQuiz(Long quizId);
}
