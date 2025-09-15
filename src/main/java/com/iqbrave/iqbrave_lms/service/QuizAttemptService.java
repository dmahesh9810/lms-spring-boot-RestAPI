package com.iqbrave.iqbrave_lms.service;

import com.iqbrave.iqbrave_lms.dto.QuizAttemptDTO;
import com.iqbrave.iqbrave_lms.entity.QuizAttempt;

import java.util.List;

public interface QuizAttemptService {

    QuizAttempt startAttempt(Long quizId, Long studentId);

    QuizAttempt submitAttempt(Long attemptId, QuizAttemptDTO attemptDTO);

    List<QuizAttempt> getAttemptsByStudent(Long studentId);

    List<QuizAttempt> getAttemptsByQuiz(Long quizId);
}
